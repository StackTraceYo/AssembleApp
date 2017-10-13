package org.stacktrace.yo.group.core.group.director

import java.util.UUID

import akka.actor.{ActorContext, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.persistence.{PersistentActor, SaveSnapshotFailure, SaveSnapshotSuccess}
import com.stacktrace.yo.assemble.group.GroupProtocol.{DirectorReferenceState, GroupReference, GroupReferenceCreated}
import com.stacktrace.yo.assemble.group.Protocol
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GetState, GroupCreatedFor}
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupCreated, ListAssembleGroup}
import org.stacktrace.yo.group.core.api.handler.GroupResponseHandler
import org.stacktrace.yo.group.core.group.lookup.PersistentActorLookup
import org.stacktrace.yo.group.core.group.retrieval.GroupSearchActor
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor.CreateGroupAndReturnTo

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class AssembleGroupDirector(directorId: String = "1")(implicit ec: ExecutionContext) extends PersistentActor with ActorLogging with PersistentActorLookup {


  override def receiveCommand: Receive = receive

  override def persistenceId = s"assemble-group-director-$directorId"

  override def receive: Receive = LoggingReceive {

    case SaveSnapshotSuccess(metadata) =>
      log.debug("SnapShot Saved")
    case SaveSnapshotFailure(metadata, reason) =>
      log.debug("SnapShot Failed: {}", reason.getMessage)

    case msg@CreateAssembleGroup(hostId, groupName, groupCategory) =>
      //get a reference to the original sender
      val api = sender()
      //generate a name for the new supervisor
      val groupActorId = generateName()
      //create this new group
      log.debug("Creating Group Actor Supervisor {}", groupActorId)
      val supervisor = createGroupSupervisor(self, groupActorId)
      supervisor ! CreateGroupAndReturnTo(CreateGroup(hostId, groupName, groupCategory), createGroupHandler(api))

    case GroupCreatedFor(groupId, respondTo) =>
      log.info("Group {} Created: Storing Reference", groupId)
      val reply = respondTo
      storeReference(groupId, sender())
        .onComplete {
          case Success(x) =>
            reply ! GroupCreated(groupId)
          case Failure(e) =>
            log.warning("Failed to Store Group")
        }


    case find@FindAssembleGroup(groupId: String) =>
      val api = sender()
      val searcher = createSearcher()
      searcher.tell(find, createGroupHandler(api))

    case find@ListAssembleGroup() =>
      val api = sender()
      val searcher = createSearcher()
      searcher.tell(find, createGroupHandler(api))

    case GetState() =>
      sender() ! referenceState

  }

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

  private def supervisorName: String => String = { x => s"assemble-group-supervisor-$x" }

  private def generateName() = {
    UUID.randomUUID().toString
  }

  private def createGroupHandler(respondTo: ActorRef): ActorRef = {
    context.actorOf(AssembleGroupDirector.responseHandlerProps(respondTo))
  }

  private def createGroupSupervisor(director: ActorRef, id: String): ActorRef = {
    context.actorOf(AssembleGroupDirector.supervisorActor(self, id, ec), supervisorName(id))
  }

  private def createSearcher(): ActorRef = {
    context.actorOf(AssembleGroupDirector.searchProps(context, references.toMap, ec))
  }
}

object AssembleGroupDirector {

  case class Connect()

  def supervisorActor(director: ActorRef, id: String, ec: ExecutionContext): Props = {
    Props(new AssembleGroupSupervisor(director, id)(ec))
  }

  def responseHandlerProps(sender: ActorRef): Props = {
    Props(new GroupResponseHandler(sender))
  }

  def searchProps(searchContext: ActorContext, refs: Map[String, ActorRef], ec: ExecutionContext): Props = {
    Props(new GroupSearchActor(searchContext, refs)(ec))
  }

}
