package org.stacktrace.yo.group.core.api

import java.io.File

import akka.actor.ActorSystem
import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpecLike}
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.language.postfixOps
import scala.util.Try

class BaseGroupClientSpec extends WordSpecLike with Matchers with BeforeAndAfterEach {


  "An BaseGroupClient" must {

    "forward a create a group request into the system" in new Context {
      val classUnderTest = new BaseGroupClient(system)
      val fResponse = classUnderTest.createGroup(CreateAssembleGroup("host-name", "group-name", "category"))
      val response = Await.result(fResponse, 2 seconds)
      response.groupId should not be empty
    }

    "forward a retrieve group request into the system" in new Context {
      val classUnderTest = new BaseGroupClient(system)
      val fResponse = classUnderTest.createGroup(CreateAssembleGroup("host-name", "group-name", "category"))
      val response = Await.result(fResponse, 2 seconds)
      response.groupId should not be empty

      val fResponse2 = classUnderTest.getGroup(FindAssembleGroup(response.groupId))
      val response2 = Await.result(fResponse2, 5 seconds)

      response2 shouldEqual Some(GroupRetrieved(AssembledGroup(response.groupId, "group-name", "category")))
      response2.get.groupInformation.groupId shouldEqual response.groupId
    }

    "forward a retrieve list group request into the system" in new Context {
      val classUnderTest = new BaseGroupClient(system)

      val fResponse = classUnderTest.createGroup(CreateAssembleGroup("host-name", "group-name", "category"))
      val response = Await.result(fResponse, 2 seconds)
      response.groupId should not be empty

      val fResponse2 = classUnderTest.createGroup(CreateAssembleGroup("host-name-2", "group-name-2", "category"))
      val response2 = Await.result(fResponse2, 2 seconds)
      response2.groupId should not be empty

      val fResponse3 = classUnderTest.createGroup(CreateAssembleGroup("host-name-3", "group-name-3", "category"))
      val response3 = Await.result(fResponse3, 2 seconds)
      response3.groupId should not be empty


      val fResponse4 = classUnderTest.getGroupList(ListAssembleGroup(Seq("group-name", "group-name-2", "group-name-3")))
      val response4 = Await.result(fResponse4, 5 seconds)

      response4.groupsInformation.size shouldBe 3

      response4.groupsInformation should contain theSameElementsAs
        List(
          AssembledGroup(response.groupId, "group-name", "category"),
          AssembledGroup(response2.groupId, "group-name-2", "category"),
          AssembledGroup(response3.groupId, "group-name-3", "category")
        )

    }
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    List(
      "target/journal",
      "target/snapshots"
    ).map { s => new File(s) }
      .foreach(dir => Try(FileUtils.deleteDirectory(dir)))
  }


  override protected def afterEach(): Unit = {
    super.afterEach()
    super.beforeEach()
    List(
      "target/journal",
      "target/snapshots"
    ).map { s => new File(s) }
      .foreach(dir => Try(FileUtils.deleteDirectory(dir)))
  }

  trait Context {

    implicit val system: ActorSystem = ActorSystem()
    implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  }

}