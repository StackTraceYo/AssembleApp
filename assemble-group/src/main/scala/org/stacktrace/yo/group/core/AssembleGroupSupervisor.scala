package org.stacktrace.yo.group.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.{CreateGroup, GroupCreated}
import org.stacktrace.yo.group.core.AssembleProtocol.Group.Init

class AssembleGroupSupervisor(director: ActorRef, name: String) extends Actor with ActorLogging {

  lazy val group: ActorRef = context.actorOf(AssembleGroupSupervisor.groupProps(self))

  override def receive: Receive = {
    case msg@CreateGroup() =>
      val responseHandler = sender()
      group ! Init()
      director ! GroupCreated(name)
      responseHandler ! GroupCreated(name)
  }
}

object AssembleGroupSupervisor {

  def groupProps(supervisor: ActorRef): Props = {
    Props(new AssembleGroup(supervisor))
  }
}

