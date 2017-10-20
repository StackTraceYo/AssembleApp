package api.auth.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import api.auth.Request.{CreateUser, RetrieveUser, SignIn}
import api.auth.Response.{FailedToCreate, FailedToRetrieve, FailedToSignIn, UserCreated, UserRetrieved}
import org.stacktrace.yo.user.auth.model.{AssembleUser, LoginData}
import org.stacktrace.yo.user.auth.service.UserService
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends AbstractController(cc) {


  def index = Action {
    Ok("User Service Ready.")
  }

  def createUser: Action[CreateUser] = Action.async(parse.json[CreateUser]) { request =>
    val user = createAssembleUserFromRequest(request.body)
    userService.save(user)
      .map(userAndToken => {
        Ok(Json.toJson(UserCreated(user.id, success = true)))
          .withHeaders(("X-Asm-Auth", userAndToken._2.id.toString))
      })
      .recover {
        case e =>
          Ok(Json.toJson(FailedToCreate(request.body, "Failed To Register", success = false)))
      }
  }

  def retrieveUser: Action[RetrieveUser] = Action.async(parse.json[RetrieveUser]) { request =>
    val retrieval = request.body
    userService.retrieve(retrieval.id)
      .map {
        case Some(user) =>
          Ok(Json.toJson(UserRetrieved(user, success = true)))
        case None =>
          Ok(Json.toJson(FailedToRetrieve(retrieval, "-1", success = false)))
      }
  }

  def authenticate: Action[SignIn] = Action.async(parse.json[SignIn]) { request =>
    val retrieval = request.body
    userService.retrieve(LoginData(retrieval.email, ""))
      .map {
        case Some(userAndToken) =>
          Ok(Json.toJson(UserRetrieved(userAndToken._1, success = true)))
            .withHeaders(("X-Asm-Auth", userAndToken._2.id.toString))
        case None =>
          Ok(Json.toJson(FailedToSignIn(retrieval, "Invalid Email or Password", success = false)))
      }
  }

  private def createAssembleUserFromRequest(request: CreateUser) = {
    AssembleUser(
      UUID.randomUUID().toString,
      request.email,
      Option(request.username)
    )
  }

  private def createLoginDataFromRequest(request: SignIn) = {
    LoginData(
      request.email,
      ""
    )
  }
}