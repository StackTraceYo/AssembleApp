package api.group

import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import play.api.libs.json.{Json, OFormat}

object Response {

  sealed trait AssembleResponse

  case class GroupCreatedResponse(groupId: String, success: Boolean) extends AssembleResponse

  case class GroupListResponse(host: List[AssembledGroup], guest: List[AssembledGroup]) extends AssembleResponse

  implicit val createGroupResponseFormats: OFormat[GroupCreatedResponse] = Json.format[GroupCreatedResponse]

  implicit val listGroupResponseFormats: OFormat[GroupListResponse] = Json.format[GroupListResponse]


}
