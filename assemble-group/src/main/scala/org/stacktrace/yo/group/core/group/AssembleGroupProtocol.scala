package org.stacktrace.yo.group.core.group

import akka.actor.ActorRef

object AssembleGroupProtocol {

  object Group {

    case class Init()

    case class GroupReady()

  }

  object Creation {

    case class CreateGroup()

    case class GroupCreated(groupName: String)

    case class GroupCreatedRef(groupName: String, ref: ActorRef)

  }

}
