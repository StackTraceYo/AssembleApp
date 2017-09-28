package api.group

import play.api.libs.json.{Json, OFormat}

object Response {

  case class GroupCreatedResponse(groupId: String, success : Boolean)

  implicit val createGroupResponseFormats: OFormat[GroupCreatedResponse] = Json.format[GroupCreatedResponse]


}
