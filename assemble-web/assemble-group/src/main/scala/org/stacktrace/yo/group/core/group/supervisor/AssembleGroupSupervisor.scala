package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.{AssembleGroupState, Created}
import com.stacktrace.yo.assemble.group.Protocol._
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

class AssembleGroupSupervisor(director: ActorRef, groupId: String) extends PersistentActor with ActorLogging with GroupSupervisionStrategy {

  import context._

  private var responseHandler: Option[ActorRef] = None

  override def persistenceId: String = "assemble-group-supervisor-" + groupId

  var state: AssembleGroupState = AssembleGroupState()
  var ready: Boolean = false

  override def receiveRecover: PartialFunction[Any, Unit] = {
    case event: Event =>
      updateState(event)
    case SnapshotOffer(metadata, resetEvent: AssembleGroupState) =>
      updateStateWithSnapShot(resetEvent)
    case RecoveryCompleted =>
      log.debug(s"Recovery has completed for $persistenceId with state {} :", state)
  }

  override def receiveCommand: PartialFunction[Any, Unit] = {
    case msg@CreateGroup(hostId: String) =>
      val event = Created(hostId, groupId)
      persist(event)(updateState)
  }

  def readyToRecieve: Receive = {
    case st@GetState() =>
      sender() ! state
    case rd@Ready() =>
      sender() ! ready
  }

  val updateState: Event => Unit = {
    case evt@Created(hostId, groupid) =>
      responseHandler = Option(sender())
      log.debug("Created Group State {}", groupid)
      state = state.update(_.groupid := groupid, _.hostid := hostId)

      director ! GroupCreatedRef(groupId, self)

      responseHandler match {
        case Some(handler) =>
          handler ! GroupCreated(groupId) // tell response handler to go
          ready = true
          log.info("Supervisor {} Ready to Recieve Messages", groupId)
          become(readyToRecieve)
        case None =>
          log.error("No Handler For Response")
      }
  }


  def updateStateWithSnapShot(snapshot: AssembleGroupState): Unit = {
    state = snapshot
  }

}

