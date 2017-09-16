package org.stacktrace.yo.user.auth.model

/**
  * The user object.
  *
  * @param id    The unique ID of the user.
  * @param name  Maybe the name of the authenticated user.
  * @param email Maybe the email of the authenticated provider.
  */
case class AssembleUser(id: String, name: Option[String], email: Option[String])

