package api.group.controller

import java.util.UUID
import javax.inject.Inject

import api.group.Request.CreateGroupRequest
import api.group.Response.GroupCreatedResponse
import org.stacktrace.yo.user.auth.service.AuthTokenService
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}

class AssembleGroupController @Inject()(cc: ControllerComponents, tokenService: AuthTokenService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Group Service Ready.")
  }

  def createGroup: Action[CreateGroupRequest] = Action.async(parse.json[CreateGroupRequest]) { request =>
    val retrieval = request.body
    val auth = request.headers.get("X-Asm-Auth").getOrElse("Unauthorized")
    if (auth.equals("Unauthorized")) {
      redirectToUnauthorized
    } else {
      tokenService.findUserFromAuthToken(UUID.fromString(auth))
        .map {
          case Some(user) =>
            Ok(Json.toJson(GroupCreatedResponse(UUID.randomUUID().toString, success = true)))
          case _ =>
            Unauthorized("Unauthorized")
        }
    }
  }

  private def redirectToUnauthorized: Future[Result] = {
    Future {
      Unauthorized("Unauthorized")
    }
  }
}
