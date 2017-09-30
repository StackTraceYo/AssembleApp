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
    * Message used to respond that a group has been created
    */
  case class GroupCreated(groupId: String) extends APIMessage

  /**
    * Message used to request a group to be retrieved
    */
  case class FindAssembleGroup(groupID: String) extends APIMessage


  /**
    * Message used to respond that a group has been retrieved
    */
  case class GroupRetrieved(groupID: String) extends APIMessage

}
