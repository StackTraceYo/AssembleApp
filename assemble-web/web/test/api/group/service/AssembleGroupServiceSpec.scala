package api.group.service

import java.io.File

import akka.actor.ActorSystem
import api.group.Request.{CreateGroupRequest, ListGroupRequest}
import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpecLike}
import org.slf4j.{Logger, LoggerFactory}
import org.stacktrace.yo.user.auth.model.AssembleUser

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleGroupServiceSpec extends WordSpecLike
  with Matchers
  with BeforeAndAfterEach {

  val logger: Logger = LoggerFactory.getLogger("Test")
  implicit val as: ActorSystem = ActorSystem("testing")
  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  "The AssembleGroupService" must {

    "create groups" in {
      val classUnderTest = new AssembleGroupService(as)
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val request = CreateGroupRequest("test-group-name")
      val result = Await.result(classUnderTest.createGroup(user, request), 5 seconds)
      result.groupId should not be empty
      logger.warn("CREATED: {}", result.groupId)
    }

    "list groups" in {
      val classUnderTest = new AssembleGroupService(as)
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))

      Await.result(classUnderTest.createGroup(user, CreateGroupRequest("test-group-name-1")), 1 seconds)
      Await.result(classUnderTest.createGroup(user, CreateGroupRequest("test-group-name-2")), 1 seconds)
      Await.result(classUnderTest.createGroup(user, CreateGroupRequest("test-group-name-3")), 1 seconds)
      Await.result(classUnderTest.createGroup(user, CreateGroupRequest("test-group-name-4")), 1 seconds)

      Thread.sleep(1000)
      val result = Await.result(classUnderTest.listGroups(user, ListGroupRequest()), 1 seconds)
      result.isDefined shouldBe true
      result.get.groupsInformation.size shouldBe 4
    }

  }


  override protected def beforeEach(): Unit = {
    deleteStorageLocations()
  }

  override protected def afterEach(): Unit = {
    deleteStorageLocations()
  }

  def deleteStorageLocations(): Unit = {
    FileUtils.deleteDirectory(new File("target"))
  }
}