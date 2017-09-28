package org.stacktrace.yo.group.core.group

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.Created
import com.stacktrace.yo.assemble.group.Protocol.Event
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Group.GroupReady

class AssembleGroup(supervisor: ActorRef) extends PersistentActor with ActorLogging {

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
    case evt@Created(name: String) =>
      log.debug("Group Initialization Command Recieved")
      persist(evt)(updateState)
    case _ =>
      log.debug("Got Unknown Command")
  }

  val updateState: Event => Unit = {
    case Created(name) =>
      groupId = name
      sender() ! GroupReady()
      saveSnapshot()
  }


  override def persistenceId = "assemble-group-actor"

}
