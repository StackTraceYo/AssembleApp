package org.stacktrace.yo.group.core.group

object GroupProtocol {


  sealed trait GroupMessage

  /**
    * An ack message for when the Underlying Group Is Ready.
    *
    * from AssembleGroup
    * to AssembleGroupSupervisor
    *
    */
  case class GroupReady() extends GroupMessage



}
