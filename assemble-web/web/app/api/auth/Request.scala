package api.auth

import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateUser(email: String, password: String, username: String = "")

  case class RetrieveUser(id: String)

  case class SignIn(email: String)

  implicit val createUserFormats: OFormat[CreateUser] = Json.format[CreateUser]
  implicit val signIn: OFormat[SignIn] = Json.format[SignIn]
  implicit val retrieveUserFormats: OFormat[RetrieveUser] = Json.format[RetrieveUser]
}
