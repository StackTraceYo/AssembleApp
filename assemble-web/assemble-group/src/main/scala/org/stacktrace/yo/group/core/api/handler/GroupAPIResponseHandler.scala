package org.stacktrace.yo.group.core.api.handler

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.GroupCreated

class GroupAPIResponseHandler(requester: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case msg@GroupCreated(name) =>
      requester ! msg
      self ! PoisonPill
  }
}
