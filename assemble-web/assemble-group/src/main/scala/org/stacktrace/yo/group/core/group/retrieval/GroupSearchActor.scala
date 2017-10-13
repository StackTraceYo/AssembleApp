package org.stacktrace.yo.group.core.group.retrieval

import akka.actor.{Actor, ActorContext, ActorLogging, ActorRef, Cancellable, PoisonPill}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.GetState
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{FindAssembleGroup, GroupRetrieved, GroupsRetrieved, ListAssembleGroup}
import org.stacktrace.yo.group.core.group.retrieval.GroupSearchActor.Timeout

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class GroupSearchActor(searchContext: ActorContext, refs: Map[String, ActorRef])(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  var respondTo: ActorRef = _
  val pending: ArrayBuffer[String] = scala.collection.mutable.ArrayBuffer[String]()
  val aggregator: mutable.HashMap[String, AssembledGroup] = scala.collection.mutable.HashMap[String, AssembledGroup]()
  var timer: Cancellable = _


  override def receive: PartialFunction[Any, Unit] = {

    case FindAssembleGroup(groupId: String) =>
      log.warning(s"looking for $groupId")
      log.warning(refs.toString())
      refs.get(groupId) match {
        case Some(group) =>
          respondTo = sender()
          context.become(waitForSingleAnswer)
          group ! GetState()
          start()
        case None =>
          //          context.child(g)
          sender() ! Option.empty
      }

    case ListAssembleGroup() =>
      respondTo = sender()
      context.become(aggregateAnswers)
      searchContext.children
      refs.foreach(
        idRef => {
          pending += idRef._1
          idRef._2 ! GetState()
        }
      )
      start()
  }

  def waitForSingleAnswer: PartialFunction[Any, Unit] = {
    case state@AssembleGroupState(hi, gi, gn, c) =>
      log.info(s"Found $gi")
      respondTo ! Some(GroupRetrieved(
        AssembledGroup(gi, gn, c))
      )
      timer.cancel()
      self ! PoisonPill
    case Timeout() =>
      log.warning("Timeout")
      self ! PoisonPill
  }

  def aggregateAnswers: PartialFunction[Any, Unit] = {
    case state@AssembleGroupState(hi, gi, gn, c) =>
      val left = pending.size - 1
      log.warning(s"Waiting on : $left")
      pending -= gi
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
    val answer = aggregator.values.toList
    val retrieved = GroupsRetrieved(answer)
    respondTo ! retrieved
    self ! PoisonPill
  }

}

object GroupSearchActor {

  case class Timeout()

}
