package org.stacktrace.yo.group.core.api.handler

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}

class GroupResponseHandler(requester: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case msg@_ =>
      log.info("Replying with : {}", msg)
      requester ! msg
      self ! PoisonPill
  }
}

object GroupResponseHandler {


}


