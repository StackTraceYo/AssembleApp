package api.content

import api.content.Request.ContentRequest
import api.content.domain.Content.Category
import org.scalatest.Matchers._
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


class ContentApiSpec extends PlaySpec with GuiceOneAppPerSuite {


  "AssembleContentController" should {

    "content service is up" in {
      val home = route(app, FakeRequest(GET, "/api/content/up")).get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("Content Ready.")
    }

    "content service returns categories" in {
      val req = route(app, FakeRequest(POST, "/api/content/retrieve").withBody(Json.toJson(ContentRequest("CATEGORY")))).get

      val res = Await.result(req, 5 seconds)
      status(req) mustBe Status.OK
      contentType(req) mustBe Some("application/json")
      val created = contentAsJson(req)
      val category = Json.fromJson[Category](contentAsJson(req)).get
      category must not be null
    }

  }
}