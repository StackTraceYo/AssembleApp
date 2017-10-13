package org.stacktrace.yo.group.core.group.retrieval

import akka.actor.{Actor, ActorContext, ActorLogging, ActorRef, Cancellable, PoisonPill}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.GetState
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{FindAssembleGroup, GroupRetrieved, GroupsRetrieved, ListAssembleGroup}
import org.stacktrace.yo.group.core.group.retrieval.GroupSearchActor.Timeout

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class GroupSearchActor(searchContext: ActorContext, refs: Map[String, ActorRef])(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  var respondTo: ActorRef = _
  val pending: mutable.HashSet[String] = scala.collection.mutable.HashSet[String]()
  val aggregator: mutable.HashMap[String, AssembledGroup] = scala.collection.mutable.HashMap[String, AssembledGroup]()
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

    case ListAssembleGroup() =>
      respondTo = sender()
      context.become(aggregateAnswers)
      refs.foreach(
        tuple => {
          val key = tuple._1
          val ref = tuple._2
          log.info(s"Found Ref $key")
          pending += s"assemble-group-supervisor-$key"
          ref ! GetState()
        }
      )
      searchContext.children
        .foreach(child => {
          val name = child.path.name
          if (name.contains("assemble-group-supervisor") && !pending.contains(name)) {
            log.info(s"Found $name")
            pending += child.path.name
            child ! GetState()
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
      log.warning("Timeout")
      respondTo ! Option.empty
      self ! PoisonPill
  }

  def aggregateAnswers: PartialFunction[Any, Unit] = {
    case state@AssembleGroupState(hi, gi, gn, c) =>
      val left = pending.size - 1
      log.info(s"Waiting on : $left")
      log.info(s"Removing: $gi")
      pending.remove(s"assemble-group-supervisor-$gi")
      log.info(s"Pending: [$pending]")
      aggregator.put(gi, AssembledGroup(gi, gn, c))
      if (pending.size <= 0) {
        timer.cancel()
        finish()
      }
    case Timeout() =>
      log.warning("Timeout {}", pending.size)
      finish()
  }

  private def start(): Unit = {
    timer = context.system.scheduler.scheduleOnce(1200 millis, self, Timeout())
  }

  private def finish(): Unit = {
    if (aggregator.nonEmpty) {
      val answer = aggregator.values.toList
      val retrieved = GroupsRetrieved(answer)
      respondTo ! retrieved
    } else {
      val retrieved = GroupsRetrieved(List())
      respondTo ! retrieved
    }
    self ! PoisonPill
  }

}

object GroupSearchActor {

  case class Timeout()

}
