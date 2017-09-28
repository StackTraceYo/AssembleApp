package org.stacktrace.yo.group.core.group

object AssembleGroupProtocol {

  object Group {

    case class GroupReady()

  }

  object Creation {

    case class CreateGroup(groupName: String)


  }

}
