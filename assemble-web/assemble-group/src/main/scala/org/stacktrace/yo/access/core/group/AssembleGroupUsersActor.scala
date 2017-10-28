package org.stacktrace.yo.access.core.group

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.PersistentActor
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupUserState

/**
  * Created by Stacktraceyo on 10/16/17.
  */
class AssembleGroupUsersActor(supervisor: ActorRef, groupId: String) extends PersistentActor with ActorLogging {

  var state: AssembleGroupUserState = AssembleGroupUserState()

  override def receiveRecover = ???

  override def receiveCommand = ???

  override def persistenceId = ???
}
