package api.group

import play.api.libs.json.{Json, OFormat}

object Response {

  sealed trait AssembleResponse

  case class GroupCreatedResponse(groupId: String, success: Boolean) extends AssembleResponse

  implicit val createGroupResponseFormats: OFormat[GroupCreatedResponse] = Json.format[GroupCreatedResponse]


}
