package api.content.controller

import javax.inject.{Inject, Singleton}

import api.content.Request.ContentRequest
import api.content.domain.Content
import org.stacktrace.yo.user.auth.service.AuthTokenService
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ContentController @Inject()(cc: ControllerComponents, tokenService: AuthTokenService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Content Ready.")
  }

  def retrieveContent: Action[ContentRequest] = Action.async(parse.json[ContentRequest]) { request =>
    Future {
      request.body.contentName match {
        case "CATEGORY" =>
          Ok(Json.toJson(Content.categories))
        case "ALL" =>
          Ok(Json.toJson(Content.categories))
      }
    }
  }

  private def redirectToUnauthorized: Future[Result] = {
    Future {
      Unauthorized("Unauthorized")
    }
  }
}
