package org.stacktrace.yo.group.core.group.retrieval

import akka.actor.{Actor, ActorContext, ActorLogging, ActorRef, Cancellable, PoisonPill}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.GetState
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol._
import org.stacktrace.yo.group.core.group.retrieval.GroupSearchActor.Timeout

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class GroupSearchActor(searchContext: ActorContext, refs: Map[String, ActorRef])(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  type GroupPair = (String, String)
  var respondTo: ActorRef = _
  val pending: mutable.HashSet[GroupPair] = scala.collection.mutable.HashSet[GroupPair]()
  val aggregator: mutable.HashMap[GroupPair, AssembledGroup] = scala.collection.mutable.HashMap[GroupPair, AssembledGroup]()
  var timer: Cancellable = _

  override def receive: PartialFunction[Any, Unit] = {

    case FindAssembleGroup(groupId: String) =>
      log.info(s"Searching for $groupId")
      refs.get(groupId) match {
        case Some(group) =>
          respondTo = sender()
          context.become(waitForSingleAnswer)
          group ! GetState()
          start()
        case None =>
          log.info("No References Checking Context")
          if (searchContext.children.nonEmpty) {
            val found = searchContext.children.find(x => x.path.name.equals(groupId))
            found match {
              case Some(group) =>
                log.info("Found Child")
                respondTo = sender()
                context.become(waitForSingleAnswer)
                group ! GetState()
                start()
              case None =>
                sender() ! Option.empty
            }
          } else {
            sender() ! Option.empty
          }
      }

    case ListNamedAssembleGroups(hosted, guest) =>
      respondTo = sender()
      context.become(aggregateAnswers)
      refs.foreach(
        tuple => {
          val key = tuple._1
          val ref = tuple._2
          if (hosted.contains(key)) {
            log.info(s"Found Ref $key for hosted")
            pending += new GroupPair(s"assemble-group-supervisor-$key", GroupSearchActor.HOSTED)
            ref ! GetState()
          }
          else if (guest.contains(key)) {
            log.info(s"Found Ref $key for guest")
            pending += new GroupPair(s"assemble-group-supervisor-$key", GroupSearchActor.JOINED)
            ref ! GetState()
          }
        }
      )
      searchContext.children
        .foreach(child => {
          val name = child.path.name
          val all = hosted ++ guest
          if (name.contains("assemble-group-supervisor") && !pending.exists(gpair => gpair._1.equals(name))) {
            if (hosted.contains(name.replace("assemble-group-supervisor-", ""))) {
              log.info(s"Found $name host")
              pending += new GroupPair(child.path.name, GroupSearchActor.HOSTED)
              child ! GetState()
            }
            else if (guest.contains(name.replace("assemble-group-supervisor-", ""))) {
              log.info(s"Found $name guest")
              pending += new GroupPair(child.path.name, GroupSearchActor.JOINED)
              child ! GetState()
            }
          }
        })
      start()
  }

  def waitForSingleAnswer: PartialFunction[Any, Unit] = {
    case state@AssembleGroupState(hi, gi, gn, c) =>
      log.info(s"Found state $state")
      respondTo ! Some(GroupRetrieved(
        AssembledGroup(gi, gn, c))
      )
      timer.cancel()
      self ! PoisonPill
    case Timeout() =>
      log.info("Timeout No Groups")
      respondTo ! Option.empty
      self ! PoisonPill
  }

  def aggregateAnswers: PartialFunction[Any, Unit] = {
    case state@AssembleGroupState(hi, gi, gn, c) =>
      val left = pending.size - 1
      log.info(s"Waiting on : $left")
      log.info(s"Removing: $gi")
      pending.find(pend => pend._1.equals(s"assemble-group-supervisor-$gi")) match {
        case Some(groupPair) =>
          pending.remove(groupPair)
          aggregator.put(groupPair, AssembledGroup(gi, gn, c))
        case None =>
      }
      log.info(s"Pending: [$pending]")
      if (pending.size <= 0) {
        timer.cancel()
        finish()
      }
    case Timeout() =>
      log.warning("Timeout {} did not return", pending.size)
      finish()
  }

  private def start(): Unit = {
    timer = context.system.scheduler.scheduleOnce(1200 millis, self, Timeout())
  }

  private def finish(): Unit = {
    if (aggregator.nonEmpty) {
      val answers = aggregator.partition(answer => answer._1._2.equals(GroupSearchActor.HOSTED))
      val retrieved = NamedGroupsRetrieved(answers._1.values.toList, answers._2.values.toList)
      respondTo ! retrieved
    } else {
      val retrieved = NamedGroupsRetrieved(List(), List())
      respondTo ! retrieved
    }
    self ! PoisonPill
  }

}

object GroupSearchActor {

  case class Timeout()

  val HOSTED = "HOSTED"
  val JOINED = "JOINED"
  val ANY = "ANY"

}
