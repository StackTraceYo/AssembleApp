package org.stacktrace.yo.group.core.group.lookup

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupRetrieved
import org.stacktrace.yo.group.core.group.lookup.AssembleLookupActor.LookupGroup

class AssembleLookupActor(groupRefs: Map[String, ActorRef], groupId: String, hostId: String) extends Actor with ActorLogging {

  override def receive: PartialFunction[Any, Unit] = {
    case LookupGroup() =>
      sender() ! lookup(groupId)
      self ! PoisonPill
  }

  private def lookup(groupId: String): Option[GroupRetrieved] = {
    groupRefs.get(groupId) match {
      case Some(group) =>
        Some(GroupRetrieved(
          AssembledGroup(groupId))
        )
      case None =>
        Option.empty
    }
  }
}

object AssembleLookupActor {

  case class LookupGroup()

}

