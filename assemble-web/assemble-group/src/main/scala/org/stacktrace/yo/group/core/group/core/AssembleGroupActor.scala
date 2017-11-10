package org.stacktrace.yo.group.core.group.core

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.{AssembleGroupState, Created}
import com.stacktrace.yo.assemble.group.Protocol._
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor.GroupReady

class AssembleGroupActor(supervisor: ActorRef, groupId: String) extends PersistentActor with ActorLogging {

  import context._

  private var responseHandler: Option[ActorRef] = None

  override def persistenceId: String = "assemble-group-actor-" + groupId

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
    case msg@CreateGroup(hostId, name, category, details) =>
      val event = Created(hostId, groupId, name, category, details.max)
      persist(event)(updateState)
    case st@GetState() =>
      sender() ! state
  }

  def readyToRecieve: Receive = {
    case st@GetState() =>
      sender() ! state
    case rd@Ready() =>
      sender() ! ready
  }

  val updateState: Event => Unit = {
    case evt@Created(hostId, groupid, name, category, max) =>
      log.debug("Created Group State {}", groupid)
      state = state.update(_.groupid := groupid, _.hostid := hostId, _.groupName := name, _.category := category, _.max := max)
      log.info("Group {} Ready to Recieve Messages", groupId)
      supervisor ! GroupReady(groupId)
      ready = true
      become(readyToRecieve)
  }


  def updateStateWithSnapShot(snapshot: AssembleGroupState): Unit = {
    state = snapshot
  }

}

object AssembleGroupActor {

  case class GroupReady(id: String)

}

