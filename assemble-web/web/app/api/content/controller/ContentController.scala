package api.content.controller

import javax.inject.{Inject, Singleton}

import api.content.Request.ContentRequest
import api.content.domain.Content
import play.api.Logger
import play.api.cache.AsyncCacheApi
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ContentController @Inject()(cache: AsyncCacheApi, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Content Ready.")
  }

  def retrieveContent: Action[ContentRequest] = Action.async(parse.json[ContentRequest]) { request =>

    cache.getOrElseUpdate[JsValue](request.body.contentName) {
      Future {
        Logger.info(s"Getting from Cache: $request.body.contentName")
        val value =
          request.body.contentName match {
            case "CATEGORY" =>
              Json.toJson(Content.categories)
            case "ALL" =>
              Json.toJson(Content.categories)
          }
        cache.set(request.body.contentName, value)
        value
      }
    }.map(res => {
      Ok(res)
    })
  }


  private def redirectToUnauthorized: Future[Result] = {
    Future {
      Unauthorized("Unauthorized")
    }
  }
}
