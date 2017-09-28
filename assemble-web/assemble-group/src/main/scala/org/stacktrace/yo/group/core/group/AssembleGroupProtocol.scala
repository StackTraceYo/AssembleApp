package org.stacktrace.yo.group.core.group

object AssembleGroupProtocol {

  object Group {


    /**
      * An ack message for when the Underlying Group Is Ready.
      *
      * from AssembleGroup
      * to AssembleGroupSupervisor
      *
      */
    case class GroupReady()

  }

}
