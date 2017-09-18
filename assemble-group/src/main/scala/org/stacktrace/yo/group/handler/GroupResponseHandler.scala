package org.stacktrace.yo.group.handler

import akka.actor.{Actor, ActorLogging, ActorRef}
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.GroupCreated

class GroupResponseHandler(requester: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg@GroupCreated(name) =>
      requester ! msg
  }
}
