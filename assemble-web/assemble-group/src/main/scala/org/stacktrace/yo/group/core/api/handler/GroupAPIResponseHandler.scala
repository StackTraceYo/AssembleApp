package org.stacktrace.yo.group.core.api.handler

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated

class GroupAPIResponseHandler(requester: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case msg@GroupCreated(name) =>
      requester ! msg
      self ! PoisonPill
  }
}

object GroupAPIResponseHandler {

  case class GroupCreated(groupName: String)

}


