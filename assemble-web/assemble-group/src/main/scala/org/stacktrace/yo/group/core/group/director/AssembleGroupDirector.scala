package org.stacktrace.yo.group.core.group.director

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import com.stacktrace.yo.assemble.group.Protocol.CreateGroup
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupRetrieved}
import org.stacktrace.yo.group.core.api.handler.GroupResponseHandler
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef
import org.stacktrace.yo.group.core.group.lookup.{ActorLookup, AssembleLookupActor}
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor

class AssembleGroupDirector extends Actor with ActorLogging with ActorLookup {


  override def receive: Receive = LoggingReceive {

    case msg@CreateAssembleGroup(hostId: String, groupName: String) =>
      //get a reference to the original sender
      val api = sender()
      //generate a name for the new supervisor
      val groupActorId = generateName()
      //create this new group
      log.debug("Creating Group Actor Supervisor {}", groupActorId)
      val supervisor = createGroupSupervisor(self, groupActorId)
      //create a new response handler for this request which will deal with sending back a response to the sender
      //forward the message
      supervisor.tell(CreateGroup(hostId), createGroupHandler(api))

    case GroupCreatedRef(groupId, actorRef) =>
      storeReference(supervisorName(groupId), actorRef)

    case FindAssembleGroup(groupId: String) =>
      val api = sender()
      val answer = getSupervisor(groupId) match {
        case Some(group) =>
          Some(GroupRetrieved(
            AssembledGroup(groupId))
          )
        case None =>
          Option.empty
      }
      sender() ! answer
  }

  def supervisorName: String => String = { x => s"assemble-group-supervisor-$x" }

  private def getSupervisor(groupId: String) = {
    findChild(s"assemble-group-supervisor-$groupId")
  }

  private def findChild(name: String) = {
    resolveActorByNameOrId(context, name)
  }

  private def generateName() = {
    UUID.randomUUID().toString
  }

  private def createGroupHandler(respondTo: ActorRef): ActorRef = {
    context.actorOf(AssembleGroupDirector.responseHandlerProps(respondTo))
  }

  private def createGroupSupervisor(director: ActorRef, id: String): ActorRef = {
    context.actorOf(AssembleGroupDirector.supervisorActor(self, id), supervisorName(id))
  }


  private def createLookupHandler(groupId: String, hostId: String): ActorRef = {
    context.actorOf(AssembleGroupDirector.lookupProps(references.toMap, groupId, hostId))
  }
}

object AssembleGroupDirector {

  def supervisorActor(director: ActorRef, id: String): Props = {
    Props(new AssembleGroupSupervisor(director, id))
  }

  def responseHandlerProps(sender: ActorRef): Props = {
    Props(new GroupResponseHandler(sender))
  }

  def lookupProps(groupRefs: Map[String, ActorRef], groupId: String, hostId: String): Props = {
    Props(new AssembleLookupActor(groupRefs, groupId, hostId))
  }

  case class GroupCreatedRef(groupName: String, ref: ActorRef)

}
