package org.stacktrace.yo.access.core.access

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.PersistentActor
import com.stacktrace.yo.assemble.access.AccessProtocol.CreatedAccess
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroupAccess, GetState}
import org.stacktrace.yo.access.core.access.GroupAccessActor.{GetAccessForUser, GetGuestAccessForUser, GetHostAccessForUser, GroupTypeAccess}
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{ListAssembleGroup, ListUserAssembleGroup}

/**
  * Created by Stacktraceyo on 10/16/17.
  */
class GroupAccessActor(director: ActorRef, id: String = "1") extends PersistentActor with ActorLogging with PersistentAccess {

  override def persistenceId = s"group-access-$id"

  override def receiveCommand: Receive = {
    case msg@CreateGroupAccess(hostId, groupId) =>
      val event = CreatedAccess(hostId, groupId)
      persist(event)(updateState)
      saveSnapshot(state)
    case ListUserAssembleGroup(userId) =>
      val access = getAccess(userId)
      log.info("GOT ACCESS")
      log.info(access.toString)
      val list = access.host ++ access.guest
      director.tell(ListAssembleGroup(list), sender())
    case GetAccessForUser(id: String) =>
      val access = getAccess(id)
      sender() ! access
    case GetHostAccessForUser(id: String) =>
      sender() ! GroupTypeAccess("HOST", getAccess(id).host)
    case GetGuestAccessForUser(id: String) =>
      sender() ! GroupTypeAccess("GUEST", getAccess(id).guest)
    case GetState() =>
      sender() ! state
  }

}

object GroupAccessActor {

  sealed trait Access

  case class GetAccessForUser(id: String) extends Access

  case class GetHostAccessForUser(id: String) extends Access

  case class GetGuestAccessForUser(id: String) extends Access

  case class GroupAccess(host: Seq[String], guest: Seq[String]) extends Access

  case class GroupTypeAccess(aType: String, access: Seq[String]) extends Access

}
