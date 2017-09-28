package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.Created
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, Event}
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated
import org.stacktrace.yo.group.core.group.AssembleGroup
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Group.GroupReady
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

class AssembleGroupSupervisor(director: ActorRef) extends PersistentActor with ActorLogging with GroupSupervisionStrategy {

  import context._

  private var responseHandler: Option[ActorRef] = None

  override def persistenceId = "assemble-group-supervisor"

  val group: ActorRef = context.actorOf(AssembleGroupSupervisor.groupProps(self))
  var ready: Boolean = false
  var groupid: String = _


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
    case msg@CreateGroup(groupName: String) =>
      val event = Created(groupName)
      persist(event)(updateState)
  }

  val updateState: Event => Unit = {
    case evt@Created(groupName) =>
      groupid = groupName
      responseHandler = Option(sender())
      log.debug("Created Group State {}", groupid)
      group ! evt
      become(waitForInit)
  }


  def waitForInit: Receive = {
    case GroupReady() =>
      director ! GroupCreatedRef(groupid, group) //tell director the group is ready
      responseHandler match {
        case Some(handler) =>
          handler ! GroupCreated(groupid) // tell response handler to go
          ready = true
          log.info("Supervisor {} Ready to Recieve Messages", groupid)
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

