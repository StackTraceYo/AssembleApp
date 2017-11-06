package org.stacktrace.yo.group.core.api

import play.api.libs.json.{Json, OFormat}

/**
  * Created by Stacktraceyo on 9/27/17.
  *
  * These models are the the api models
  *
  */
object GroupAPIModel {

  case class AssembledGroup(groupId: String, name: String, category: String, max: Int)

  implicit val assembleGroupResponse: OFormat[AssembledGroup] = Json.format[AssembledGroup]


}
