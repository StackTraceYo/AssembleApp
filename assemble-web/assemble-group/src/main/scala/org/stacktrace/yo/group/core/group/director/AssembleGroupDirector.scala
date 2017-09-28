package org.stacktrace.yo.group.core.group.director

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import org.stacktrace.yo.group.core.api.AssembleGroupAPIProtocol.CreateAssembleGroup
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.CreateGroup
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor

import scala.collection.mutable

class AssembleGroupDirector extends Actor with ActorLogging {

  val groupRefs: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()

  override def receive: Receive = LoggingReceive {
    case msg@CreateAssembleGroup() =>
      //get a reference to the original sender
      val api = sender()
      //generate a name for the new supervisor
      val name = generateName()
      //create supervisor for this new group
      val supervisor = context.actorOf(AssembleGroupDirector.supervisionProps(self))
      //create a new response handler for this request which will deal with sending back a response to the sender
      //forward the message
      supervisor.tell(CreateGroup(name), createGroupHandler(api))
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

  def supervisionProps(director: ActorRef): Props = {
    Props(new AssembleGroupSupervisor(director))
  }

  def responseHandlerProps(sender: ActorRef): Props = {
    Props(new GroupAPIResponseHandler(sender))
  }

  case class GroupCreatedRef(groupName: String, ref: ActorRef)

}
