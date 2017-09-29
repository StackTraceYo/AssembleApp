package org.stacktrace.yo.group.core.group.director

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import com.stacktrace.yo.assemble.group.Protocol.CreateGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.CreateAssembleGroup
import org.stacktrace.yo.group.core.api.handler.GroupResponseHandler
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

import scala.collection.mutable

class AssembleGroupDirector extends Actor with ActorLogging {

  val groupRefs: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()

  override def receive: Receive = LoggingReceive {
    case msg@CreateAssembleGroup(hostId: String, groupName: String) =>
      //get a reference to the original sender
      val api = sender()
      //generate a name for the new supervisor
      val groupActorId = generateName()
      //create this new group
      log.debug("Creating Group Actor {}", groupActorId)
      val supervisor = context.actorOf(AssembleGroupDirector.groupActor(self, groupActorId))
      //create a new response handler for this request which will deal with sending back a response to the sender
      //forward the message
      supervisor.tell(CreateGroup(hostId), createGroupHandler(api))
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

  def groupActor(director: ActorRef, id: String): Props = {
    Props(new AssembleGroupActor(director, id))
  }

  def responseHandlerProps(sender: ActorRef): Props = {
    Props(new GroupResponseHandler(sender))
  }

  case class GroupCreatedRef(groupName: String, ref: ActorRef)

}
