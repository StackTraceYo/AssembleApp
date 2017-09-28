package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.Created
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, Event}
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated
import org.stacktrace.yo.group.core.group.AssembleGroup
import org.stacktrace.yo.group.core.group.GroupProtocol.Group.GroupReady
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

class AssembleGroupSupervisor(director: ActorRef) extends PersistentActor with ActorLogging with GroupSupervisionStrategy {

  import context._

  private var responseHandler: Option[ActorRef] = None

  override def persistenceId = "assemble-group-supervisor"

  val groupState: ActorRef = context.actorOf(AssembleGroupSupervisor.groupProps(self))
  var ready: Boolean = false
  var groupId: String = _


  override def receiveRecover: PartialFunction[Any, Unit] = {
    case event: Event =>
    //      updateState(event)
    // comes from the snapshot journal
    case SnapshotOffer(metadata, resetEvent: Event) =>
    //      updateState(resetEvent)
    // this message is sent once recovery has completed
    case RecoveryCompleted =>
      log.debug(s"Recovery has completed for $persistenceId")
  }

  override def receiveCommand: PartialFunction[Any, Unit] = {
    case msg@CreateGroup(groupId: String, hostId: String) =>
      val event = Created(groupId)
      persist(event)(updateState)
  }

  val updateState: Event => Unit = {
    case evt@Created(hostId, groupid) =>
      groupId = groupid
      responseHandler = Option(sender())
      log.debug("Created Group State {}", groupid)
      groupState ! evt
      become(waitForInit)
  }


  def waitForInit: Receive = {
    case GroupReady() =>
      director ! GroupCreatedRef(groupId, groupState) //tell director the group is ready
      responseHandler match {
        case Some(handler) =>
          handler ! GroupCreated(groupId) // tell response handler to go
          ready = true
          log.info("Supervisor {} Ready to Recieve Messages", groupId)
          become(initializedReceiveCommand)
        case None =>
          log.error("No Handler For Response")
      }
  }

  def initializedReceiveCommand: Receive = {
    case _ =>
  }
}

object AssembleGroupSupervisor {

  def groupProps(supervisor: ActorRef): Props = {
    Props(new AssembleGroup(supervisor))
  }
}

