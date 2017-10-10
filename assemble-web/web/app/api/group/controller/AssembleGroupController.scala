package api.group.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import api.group.Request.{CreateGroupRequest, ListGroupRequest}
import api.group.Response.{GroupCreatedResponse, GroupListResponse}
import api.group.service.AssembleGroupService
import org.stacktrace.yo.user.auth.service.AuthTokenService
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AssembleGroupController @Inject()(cc: ControllerComponents, tokenService: AuthTokenService, groupService: AssembleGroupService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Group Service Ready.")
  }

  def createGroup: Action[CreateGroupRequest] = Action.async(parse.json[CreateGroupRequest]) { request =>
    val creationRequest = request.body
    val auth = request.headers.get("X-Asm-Auth").map(header=> header.replace("\"","")).getOrElse("Unauthorized")
    if (auth.equals("Unauthorized")) {
      redirectToUnauthorized
    } else {
      tokenService.findUserFromAuthToken(UUID.fromString(auth))
        .flatMap {
          case Some(user) =>
            groupService.createGroup(user, creationRequest)
              .map(groupAnswer => {
                Ok(Json.toJson(GroupCreatedResponse(groupAnswer.groupId, success = true)))
              })
          //            Ok(Json.toJson(GroupCreatedResponse(UUID.randomUUID().toString, success = true)))
          case _ =>
            Future {
              Unauthorized("Unauthorized")
            }
        }
    }
  }

  def listGroups: Action[ListGroupRequest] = Action.async(parse.json[ListGroupRequest]) { request =>
    val listRequest = request.body
    val auth = request.headers.get("X-Asm-Auth").map(header=> header.replace("\"","")).getOrElse("Unauthorized")
    if (auth.equals("Unauthorized")) {
      redirectToUnauthorized
    } else {
      tokenService.findUserFromAuthToken(UUID.fromString(auth))
        .flatMap {
          case Some(user) =>
            groupService.listGroups(user, listRequest)
              .map(answer => {
                Ok(Json.toJson(GroupListResponse(answer.groupsInformation)))
              })
          case _ =>
            Future {
              Unauthorized("Unauthorized")
            }
        }
    }
  }

  private def redirectToUnauthorized: Future[Result] = {
    Future {
      Unauthorized("Unauthorized")
    }
  }
}
