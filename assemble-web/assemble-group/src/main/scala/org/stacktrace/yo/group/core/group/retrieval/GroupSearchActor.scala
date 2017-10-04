package org.stacktrace.yo.group.core.group.retrieval

import akka.actor.{Actor, ActorContext, ActorLogging, ActorRef}
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{FindAssembleGroup, GroupRetrieved, GroupsRetrieved, ListAssembleGroup}

class GroupSearchActor(searchContext: ActorContext, refs: Map[String, ActorRef]) extends Actor with ActorLogging {

  override def receive: PartialFunction[Any, Unit] = {

    case FindAssembleGroup(groupId: String) =>

      val responseHandler = sender()
      val answer = refs.get(groupId) match {
        case Some(group) =>``
          Some(GroupRetrieved(
            AssembledGroup(groupId))
          )
        case None =>
          Option.empty
      }
      sender() ! answer

    case ListAssembleGroup() =>

      val answer = refs.map(
        idRef => {
          AssembledGroup(idRef._1)
        }
      ).toList
      val retrieved = GroupsRetrieved(answer)
      sender() ! retrieved
  }
}
