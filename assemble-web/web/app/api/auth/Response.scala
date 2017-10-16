package api.auth

import api.auth.Request.{CreateUser, RetrieveUser, SignIn}
import org.stacktrace.yo.user.model.AssembleUser
import play.api.libs.json.{Json, OFormat}

object Response {

  case class UserCreated(id: String, success: Boolean)

  case class FailedToCreate(request: CreateUser, msg: String, success: Boolean)

  case class UserRetrieved(user: AssembleUser, success: Boolean)

  case class FailedToRetrieve(request: RetrieveUser, msg: String, success: Boolean)

  case class FailedToSignIn(request: SignIn, msg: String, success: Boolean)

  implicit val userCreatedFormats: OFormat[UserCreated] = Json.format[UserCreated]
  implicit val failUserCreatedFormats: OFormat[FailedToCreate] = Json.format[FailedToCreate]
  implicit val userRetrievedFormats: OFormat[UserRetrieved] = Json.format[UserRetrieved]
  implicit val failUserRetrievedFormats: OFormat[FailedToRetrieve] = Json.format[FailedToRetrieve]
  implicit val failedToSignInFormats: OFormat[FailedToSignIn] = Json.format[FailedToSignIn]


}
