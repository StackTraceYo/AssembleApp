package org.stacktrace.yo.group.core

object AssembleProtocol {

  object Group {

    case class Init()

  }

  object Creation {

    case class CreateGroup()

    case class GroupCreated(groupName: String)

  }

}
