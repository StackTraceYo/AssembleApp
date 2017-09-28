package api.group.controller

import javax.inject.Inject

import api.group.Request.CreateGroupRequest
import api.group.Response.GroupCreatedResponse
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class AssembleGroupController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Group Service Ready.")
  }

  def createGroup: Action[CreateGroupRequest] = Action.async(parse.json[CreateGroupRequest]) { request =>
    val retrieval = request.body
    Future {
      Ok(Json.toJson(GroupCreatedResponse("test")))
    }
  }
}
