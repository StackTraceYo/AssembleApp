package org.stacktrace.yo.group.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.{CreateGroup, GroupCreated, GroupCreatedRef}
import org.stacktrace.yo.group.core.AssembleProtocol.Group.{GroupReady, Init}

class AssembleGroupSupervisor(director: ActorRef, name: String) extends Actor with ActorLogging with GroupSupervisionStrategy {

  import context._

  private var responseHandler: Option[ActorRef] = None
  lazy val group: ActorRef = context.actorOf(AssembleGroupSupervisor.groupProps(self))
  var ready: Boolean = false

  override def receive: Receive = {
    case msg@CreateGroup() =>
      responseHandler = Option(sender())
      group ! Init() //init group
      become(waitForInit)
  }

  def waitForInit: Receive = {
    case GroupReady() =>
      log.info("Group Ready")
      director ! GroupCreatedRef(name, group) //tell director the group is ready
      responseHandler match {
        case Some(handler) =>
          handler ! GroupCreated(name) // tell response handler to go
          ready = true
          log.info("Group Ready to Recieve Messages")
          become(initialized)
        case None =>
          log.error("No Handler For Response")
      }
  }

  def initialized: Receive = {
    case _ =>
  }
}

object AssembleGroupSupervisor {

  def groupProps(supervisor: ActorRef): Props = {
    Props(new AssembleGroup(supervisor))
  }
}

