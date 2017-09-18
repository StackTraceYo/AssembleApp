package org.stacktrace.yo.group.core

import akka.actor.{Actor, ActorLogging, ActorRef}
import org.stacktrace.yo.group.core.AssembleProtocol.Group.Init

class AssembleGroup(supervisor: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case Init() =>
      log.info("Group Initialized")
  }
}
