package org.stacktrace.yo.group.core.api

import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup

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
  case class CreateAssembleGroup(hostId: String, groupName: String, groupCategory: String) extends APIMessage

  /**
    * Message used to respond that a group has been created
    */
  case class GroupCreated(groupId: String) extends APIMessage

  /**
    * Message used to request a group to be retrieved
    */
  case class FindAssembleGroup(groupID: String) extends APIMessage

  /**
    * Message used to request to list groups
    */
  case class ListAssembleGroup() extends APIMessage


  /**
    * Message used to respond that a group has been retrieved
    */
  case class GroupRetrieved(groupInformation: AssembledGroup) extends APIMessage

  /**
    * Message used to respond that a group has been retrieved
    */
  case class GroupsRetrieved(groupsInformation: List[AssembledGroup]) extends APIMessage

}
