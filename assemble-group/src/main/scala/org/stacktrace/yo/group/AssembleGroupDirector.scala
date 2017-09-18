package org.stacktrace.yo.group

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import org.stacktrace.yo.group.core.AssembleGroupSupervisor
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.{CreateGroup, GroupCreated}
import org.stacktrace.yo.group.handler.GroupResponseHandler

import scala.collection.mutable

class AssembleGroupDirector extends Actor with ActorLogging {

  val groupRefs: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()

  override def receive: Receive = LoggingReceive {
    case msg@CreateGroup() =>
      val ogSender = sender()
      val name = generateName()
      val supervisor = context.actorOf(AssembleGroupDirector.supervisionProps(self, name))
      supervisor.tell(msg, context.actorOf(AssembleGroupDirector.responseHandlerProps(ogSender)))
    case GroupCreated(groupName) =>

  }

  private def generateName() = {
    UUID.randomUUID().toString
  }
}

object AssembleGroupDirector {

  def supervisionProps(director: ActorRef, name: String): Props = {
    Props(new AssembleGroupSupervisor(director, name))
  }

  def responseHandlerProps(sender: ActorRef): Props = {
    Props(new GroupResponseHandler(sender))
  }
}
