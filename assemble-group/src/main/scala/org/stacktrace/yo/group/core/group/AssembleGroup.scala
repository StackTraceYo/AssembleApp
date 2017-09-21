package org.stacktrace.yo.group.core.group

import akka.actor.{Actor, ActorLogging, ActorRef}
import AssembleGroupProtocol.Group.{GroupReady, Init}

class AssembleGroup(supervisor: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Init() =>
      val ogSender = sender()
      log.warning("Group Initialized")
      ogSender ! GroupReady()

  }
}
