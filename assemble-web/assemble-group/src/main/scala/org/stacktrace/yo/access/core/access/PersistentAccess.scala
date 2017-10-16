package org.stacktrace.yo.access.core.access

import akka.actor.{Actor, ActorLogging}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.access.AccessProtocol.{AccessReference, CreatedAccess, GroupAccessState}
import com.stacktrace.yo.assemble.group.Protocol.{AccessEvent, CreateGroupAccess, GetState}

import scala.language.postfixOps

trait PersistentAccess {

  this: PersistentActor with Actor with ActorLogging =>

  var state: GroupAccessState = GroupAccessState()


  override def receiveRecover: PartialFunction[Any, Unit] = {
    case event: AccessEvent =>
      log.debug(s"Recovering Event -> $event")
      updateState(event)
    case SnapshotOffer(metadata, state: GroupAccessState) =>
      log.debug(s"Recovering Snapshot-> $state")
      rebuildState(state)
    case RecoveryCompleted =>
      log.debug(s"Recovery has completed for $persistenceId with reference size {} :", state.groups.size)
  }

  override def receiveCommand: Receive = {
    case msg@CreateGroupAccess(hostId, groupId) =>
      val event = CreatedAccess(hostId, groupId)
      persist(event)(updateState)
      saveSnapshot(state)
    case GetState() =>
      sender() ! state
  }

  val updateState: AccessEvent => Unit = {
    case evt@CreatedAccess(host, group) =>
      log.debug(s"Created Group Access $group with host $host")
      val newAccess = state.groups.get(host) match {
        case Some(ref) =>
          AccessReference((ref.host :+ group).distinct, ref.guest)
        case None =>
          AccessReference(List(group), List())
      }
      val entry = host -> newAccess
      state = state.update(_.groups := state.groups + entry)
  }

  def rebuildState(snapShot: GroupAccessState): Unit = {
    state = snapShot
  }


}
