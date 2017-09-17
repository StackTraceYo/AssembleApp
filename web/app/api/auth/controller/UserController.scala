package api.auth.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import api.auth.Request.{CreateUser, RetrieveUser}
import api.auth.Response.{FailedToRetrieve, UserCreated, UserRetrieved}
import org.stacktrace.yo.user.auth.model.AssembleUser
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
      .map(user => {
        Ok(Json.toJson(UserCreated(user.id, success = true)))
      })
  }

  def retrieveUser: Action[RetrieveUser] = Action.async(parse.json[RetrieveUser]) { request =>
    val retrieval = request.body
    userService.retrieve(retrieval.id)
      .map {
        case Some(user) =>
          Ok(Json.toJson(UserRetrieved(user, success = true)))
        case None =>
          Ok(Json.toJson(FailedToRetrieve(retrieval, success = false)))
      }
  }

  private def createAssembleUserFromRequest(request: CreateUser) = {
    AssembleUser(
      UUID.randomUUID().toString,
      Option(request.username),
      Option(request.email)
    )
  }
}