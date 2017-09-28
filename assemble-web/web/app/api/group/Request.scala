package api.group

import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateGroupRequest(groupName: String)

  implicit val createGroupRequestFormats: OFormat[CreateGroupRequest] = Json.format[CreateGroupRequest]

}
