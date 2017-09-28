package api.group

import play.api.libs.json.{Json, OFormat}

object Request {

  sealed trait AssembleRequest

  case class CreateGroupRequest(groupName: String) extends AssembleRequest

  implicit val createGroupRequestFormats: OFormat[CreateGroupRequest] = Json.format[CreateGroupRequest]

}
