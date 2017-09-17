package org.stacktrace.yo.user.auth.model

import play.api.libs.json.{Json, OFormat}

/**
  * The user object.
  *
  * @param id       The unique ID of the user.
  * @param username Maybe the name of the authenticated user.
  * @param email    Maybe the email of the authenticated provider.
  */
case class AssembleUser(id: String, username: Option[String], email: Option[String])

object AssembleUser {
  implicit val userFormats: OFormat[AssembleUser] = Json.format[AssembleUser]
}

