package api.content

import play.api.libs.json.{Json, OFormat}

object Request {

  case class ContentRequest(contentName: String = "ALL")

  implicit val contentFormats: OFormat[ContentRequest] = Json.format[ContentRequest]
}
