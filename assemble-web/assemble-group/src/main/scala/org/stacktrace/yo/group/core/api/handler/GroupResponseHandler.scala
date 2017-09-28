package org.stacktrace.yo.group.core.api.handler

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated

class GroupResponseHandler(requester: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case msg@GroupCreated(id) =>
      log.debug("Replying with : {}", msg)
      requester ! msg
      self ! PoisonPill
  }
}

object GroupResponseHandler {


}


