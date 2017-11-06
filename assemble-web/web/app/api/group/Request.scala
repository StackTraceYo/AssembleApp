package api.group

import play.api.libs.json.{Json, OFormat}

object Request {

  sealed trait AssembleRequest

  case class CreateGroupDetails(max: Int)

  implicit val detailsFormats: OFormat[CreateGroupDetails] = Json.format[CreateGroupDetails]

  case class CreateGroupRequest(groupName: String, categoryName: String, details: CreateGroupDetails) extends AssembleRequest

  case class ListGroupRequest(count: Int = Int.MaxValue, page: Int = 1, offset: Int = 0) extends AssembleRequest

  implicit val createGroupRequestFormats: OFormat[CreateGroupRequest] = Json.format[CreateGroupRequest]

  implicit val listGroupRequestFormats: OFormat[ListGroupRequest] = Json.format[ListGroupRequest]

}
