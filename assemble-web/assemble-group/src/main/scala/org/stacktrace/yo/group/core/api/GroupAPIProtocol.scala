package org.stacktrace.yo.group.core.api

/**
  * Created by Stacktraceyo on 9/27/17.
  *
  * These messages are the bridge between the Assemble-Group Actor System and the API
  *
  */
object GroupAPIProtocol {

  sealed trait APIMessage

  /**
    * Message used to request a group to be created
    */
  case class CreateAssembleGroup(hostId: String, groupName: String) extends APIMessage

  /**
    * Message used to request a group has been created
    */
  case class GroupCreated(groupId: String) extends APIMessage

}
