package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.util.Timeout
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GroupCreatedFor}
import org.stacktrace.yo.access.core.group.AssembleGroupUsersActor
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor.GroupReady
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor.CreateGroupAndReturnTo

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by Stacktraceyo on 9/29/17.
  */
class AssembleGroupSupervisor(director: ActorRef, groupId: String)(implicit ec: ExecutionContext) extends Actor with ActorLogging with GroupSupervisionStrategy {
  private implicit val timeout: Timeout = Timeout(3 seconds)
  val groupActor: ActorRef = context.actorOf(AssembleGroupSupervisor.groupActorProps(self, groupId), s"group-$groupId")
//  val groupUsersActor: ActorRef = context.actorOf(AssembleGroupSupervisor.groupUserProps(self, groupId), s"group-user-$groupId")
  var returnTo: ActorRef = _

  override def receive: PartialFunction[Any, Unit] = {
    case CreateGroupAndReturnTo(create: CreateGroup, ref: ActorRef) =>
      returnTo = ref
      context.become(waitForInit)
      groupActor ! create
    case fwd@_ =>
      groupActor.tell(fwd, sender)
  }

  def waitForInit: PartialFunction[Any, Unit] = {
    case GroupReady(id) =>
      log.info("Group Supervisor Ready" + " " + returnTo.path.toSerializationFormat)
      context.become(receive)
      director ! GroupCreatedFor(groupId, returnTo)
    //      returnTo = director
  }

}

object AssembleGroupSupervisor {

  def groupActorProps(supervisor: ActorRef, id: String): Props = {
    Props(new AssembleGroupActor(supervisor, id))
  }

  def groupUserProps(supervisor: ActorRef, id: String): Props = {
    Props(new AssembleGroupUsersActor(supervisor, id))
  }

  case class CreateGroupAndReturnTo(create: CreateGroup, retTo: ActorRef)

}
