package org.stacktrace.yo.group

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import org.stacktrace.yo.group.core.AssembleGroupSupervisor
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.{CreateGroup, GroupCreatedRef}
import org.stacktrace.yo.group.handler.GroupResponseHandler

import scala.collection.mutable

class AssembleGroupDirector extends Actor with ActorLogging {

  val groupRefs: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()

  override def receive: Receive = LoggingReceive {
    case msg@CreateGroup() =>
      //get a reference to the original sender
      val api = sender()
      //generate a name for the new supervisor
      val name = generateName()
      //create supervisor for this new group
      val supervisor = context.actorOf(AssembleGroupDirector.supervisionProps(self, name))
      //create a new response handler for this request which will deal with sending back a response to the sender
      //forward the message
      supervisor.tell(msg, createGroupHandler(api))
    case GroupCreatedRef(groupName, actorRef) =>
      groupRefs.put(groupName, actorRef)

  }

  private def generateName() = {
    UUID.randomUUID().toString
  }

  private def createGroupHandler(respondTo: ActorRef): ActorRef = {
    context.actorOf(AssembleGroupDirector.responseHandlerProps(respondTo))
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
