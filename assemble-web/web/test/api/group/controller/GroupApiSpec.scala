package api.group.controller

import java.io.File
import java.util.UUID

import api.auth.Request.CreateUser
import api.group.Request.{CreateGroupRequest, ListGroupRequest}
import api.group.Response.{GroupCreatedResponse, GroupListResponse}
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach
import org.scalatest.Matchers._
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


class GroupApiSpec extends PlaySpec with GuiceOneAppPerTest with BeforeAndAfterEach {


  "AssembleGroupController" should {

    "group service is up" in {
      val home = route(app, FakeRequest(GET, "/api/group/up")).get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("Group Service Ready.")
    }

    "authorized user can create a group" in {
      val user = route(app, FakeRequest(POST, "/api/auth/user/create").withBody(Json.toJson(CreateUser(UUID.randomUUID().toString, UUID.randomUUID().toString)))).get
      val res = Await.result(user, 5 seconds)

      val headerVal = res.header.headers("X-Asm-Auth")

      val create = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category")))
        .withHeaders("X-Asm-Auth" -> headerVal))
        .get

      val created = Json.fromJson[GroupCreatedResponse](contentAsJson(create)).get

      created shouldBe a[GroupCreatedResponse]
      created.success shouldBe true
      created.groupId should not be empty
    }

    "not authorized user can not create a group" in {
      val create = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category")))
        .withHeaders("X-Asm-Auth" -> UUID.randomUUID().toString))
        .get

      status(create) mustBe Status.UNAUTHORIZED
    }

    "not authorized user can not create a group without header" in {
      val create = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category"))))
        .get

      status(create) mustBe Status.UNAUTHORIZED
    }

    "authorized user can see groups" in {
      val user = route(app, FakeRequest(POST, "/api/auth/user/create").withBody(Json.toJson(CreateUser(UUID.randomUUID().toString, UUID.randomUUID().toString)))).get
      val res = Await.result(user, 5 seconds)

      val headerVal = res.header.headers("X-Asm-Auth")

      val create = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category")))
        .withHeaders("X-Asm-Auth" -> headerVal))
        .get

      val create2 = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category")))
        .withHeaders("X-Asm-Auth" -> headerVal))
        .get

      val create3 = route(app, FakeRequest(POST, "/api/group/create")
        .withBody(Json.toJson(CreateGroupRequest("test-group-name", "test-group-category")))
        .withHeaders("X-Asm-Auth" -> headerVal))
        .get

      val created = Json.fromJson[GroupCreatedResponse](contentAsJson(create)).get
      val created2 = Json.fromJson[GroupCreatedResponse](contentAsJson(create2)).get
      val created3 = Json.fromJson[GroupCreatedResponse](contentAsJson(create3)).get

      val list = route(app, FakeRequest(POST, "/api/group/list")
        .withBody(Json.toJson(ListGroupRequest()))
        .withHeaders("X-Asm-Auth" -> headerVal))
        .get

      val listr = Json.fromJson[GroupListResponse](contentAsJson(list)).get

      listr shouldBe a[GroupListResponse]
      listr.host.size shouldBe 3
      print(listr)

    }
  }

  override protected def beforeEach(): Unit = {
    deleteStorageLocations()
  }

  override protected def afterEach(): Unit = {
    deleteStorageLocations()
  }

  def deleteStorageLocations(): Unit = {
    FileUtils.deleteDirectory(new File("target/journal"))
    FileUtils.deleteDirectory(new File("target/snapshots"))
  }
}