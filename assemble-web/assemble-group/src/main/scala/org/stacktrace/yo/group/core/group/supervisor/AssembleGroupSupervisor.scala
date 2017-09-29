package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.stacktrace.yo.assemble.group.Protocol.Ready
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

/**
  * Created by Stacktraceyo on 9/29/17.
  */
class AssembleGroupSupervisor(director: ActorRef, groupId: String) extends Actor with ActorLogging with GroupSupervisionStrategy {

  val groupActor: ActorRef = context.actorOf(AssembleGroupSupervisor.groupActorProps(self, groupId), "group-" + groupId)

  override def receive: PartialFunction[Any, Unit] = {
    case Ready() =>
      director ! GroupCreatedRef(groupId, self)
    case fwd@_ =>
      groupActor.tell(fwd, sender)
  }
}

object AssembleGroupSupervisor {

  def groupActorProps(supervisor: ActorRef, id: String): Props = {
    Props(new AssembleGroupActor(supervisor, id))
  }

}
