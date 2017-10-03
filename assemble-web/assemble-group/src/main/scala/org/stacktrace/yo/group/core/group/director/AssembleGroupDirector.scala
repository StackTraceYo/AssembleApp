package org.stacktrace.yo.group.core.group.director

import java.util.UUID

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.persistence.{PersistentActor, SaveSnapshotFailure, SaveSnapshotSuccess}
import com.stacktrace.yo.assemble.group.GroupProtocol.{DirectorReferenceState, GroupReference, GroupReferenceCreated}
import com.stacktrace.yo.assemble.group.Protocol
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GetState, GroupCreatedRef}
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupRetrieved}
import org.stacktrace.yo.group.core.api.handler.GroupResponseHandler
import org.stacktrace.yo.group.core.group.lookup.{AssembleLookupActor, PersistentActorLookup}
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor

import scala.concurrent.ExecutionContext

class AssembleGroupDirector(directorId: String = "1")(implicit ec: ExecutionContext) extends PersistentActor with ActorLogging with PersistentActorLookup {


  override implicit val executionContext: ExecutionContext = ec

  override def receiveCommand: Receive = receive

  override def persistenceId = s"assemble-group-director-$directorId"

  override def receive: Receive = LoggingReceive {

    case SaveSnapshotSuccess(metadata) =>
      log.debug("SnapShot Saved")
    case SaveSnapshotFailure(metadata, reason) =>
      log.debug("SnapShot Failed: {}", reason.getMessage)

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
      storeReference(groupId, actorRef)

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
    case GetState() =>
      sender() ! referenceState
  }

  def supervisorName: String => String = { x => s"assemble-group-supervisor-$x" }


  override def rebuild(event: Protocol.Event): Unit = {
    event match {
      case evt@GroupReferenceCreated(id, name, path) =>
        val supervisor = createGroupSupervisor(self, id)
        val newRef = id -> GroupReference(supervisor.path.name, supervisor.path.toSerializationFormat)
        referenceState = referenceState.update(_.reference := referenceState.reference + newRef)
        references.put(id, supervisor)
        saveSnapshot(referenceState)
    }
  }

  override def rebuild(snapshot: DirectorReferenceState): Unit = {
    referenceState = snapshot
    referenceState.reference.foreach(
      ref => {
        //rebuild each id
        val id = ref._1
        //rebuild supervisor
        //each supervisor should rebuild the persisted group
        val supervisor = createGroupSupervisor(self, id)
      })
  }

  private def getSupervisor(groupId: String) = {
    resolveActorById(groupId)
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

}
