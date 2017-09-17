package api.auth

import api.auth.Request.{CreateUser, RetrieveUser}
import api.auth.Response.{FailedToRetrieve, UserCreated, UserRetrieved}
import org.scalatest.Matchers._
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._


class AuthApiSpec extends PlaySpec with GuiceOneAppPerSuite {

  "UserController" should {

    "user service is up" in {
      val home = route(app, FakeRequest(GET, "/api/auth/user/up")).get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("User Service Ready.")
    }

    "user service can create a user" in {
      val create = route(app, FakeRequest(POST, "/api/auth/user/create").withBody(Json.toJson(CreateUser("ahmad", "test")))).get

      status(create) mustBe Status.OK
      contentType(create) mustBe Some("application/json")
      val created = Json.fromJson[UserCreated](contentAsJson(create)).get

      created shouldBe a[UserCreated]
      created.success mustBe true
      created.id must not be empty
    }

    "user service can retrieve user" in {
      val create = route(app, FakeRequest(POST, "/api/auth/user/create").withBody(Json.toJson(CreateUser("ahmad", "test")))).get
      val created = Json.fromJson[UserCreated](contentAsJson(create)).get

      val retrieve = route(app, FakeRequest(POST, "/api/auth/user/retrieve").withBody(Json.toJson(RetrieveUser(created.id)))).get

      status(retrieve) mustBe Status.OK
      contentType(retrieve) mustBe Some("application/json")
      val retrieved = Json.fromJson[UserRetrieved](contentAsJson(retrieve)).get

      retrieved shouldBe a[UserRetrieved]
      retrieved.success mustBe true
      retrieved.user.id mustBe created.id
    }

    "no user is returned on failed user" in {
      val retrieve = route(app, FakeRequest(POST, "/api/auth/user/retrieve").withBody(Json.toJson(RetrieveUser("123")))).get

      status(retrieve) mustBe Status.OK
      contentType(retrieve) mustBe Some("application/json")
      val retrieved = Json.fromJson[FailedToRetrieve](contentAsJson(retrieve)).get

      retrieved shouldBe a[FailedToRetrieve]
      retrieved.success mustBe false
      retrieved.request.id mustBe "123"
    }

  }

}