package org.stacktrace.yo.access.core.access

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence.PersistentActor

/**
  * Created by Stacktraceyo on 10/16/17.
  */
class GroupAccessActor(director: ActorRef, id: String = "1") extends PersistentActor with ActorLogging with PersistentAccess {

  override def persistenceId = s"group-access-$id"


}
