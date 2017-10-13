package org.stacktrace.yo.group.core.group.director

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.GroupProtocol.DirectorReferenceState
import com.stacktrace.yo.assemble.group.Protocol.{GetState, GroupCreatedFor}
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupCreated, GroupRetrieved}

import scala.concurrent.duration._
import scala.language.postfixOps

class AssembleGroupDirectorSpec extends AssemblePersistenceSpec(ActorSystem("directorTestSystem")) {


  "An AssembleGroupDirector Actor" must {

    "create a group" in {
      val director = newDirector("new")
      director ! CreateAssembleGroup("test-user-id", "test-group-name", "test-group-category")
      val message = expectMsgType[GroupCreated] //sender gets a group created back
    }

    "retrieve a group that was created" in {
      val director = newDirector("2")
      director ! CreateAssembleGroup("test-user-id", "test-group-name", "test-group-category")
      val message = expectMsgType[GroupCreated] //sender gets a group created back
      director ! FindAssembleGroup(message.groupId)
      //      Thread.sleep(1500)
      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Some(GroupRetrieved(AssembledGroup(message.groupId, "test-group-name", "test-group-category")))
    }

    "return an empty option when no group is found" in {
      val director = newDirector("3")
      director ! FindAssembleGroup("test-group-id2")

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Option.empty
    }

    "director state is persisted" in {
      val director = newDirector("4")
      val ref = TestProbe()
      director ! GroupCreatedFor("test-group-id", ref.ref)
      ref.expectMsgType[GroupCreated]
      director ! GroupCreatedFor("test-group-id2", ref.ref)
      ref.expectMsgType[GroupCreated]
      director ! GroupCreatedFor("test-group-id3", ref.ref)
      ref.expectMsgType[GroupCreated]
      director ! GroupCreatedFor("test-group-id4", ref.ref)
      ref.expectMsgType[GroupCreated]

      def pollForFirstState(): Boolean = {
        println("Polling..")
        director ! GetState()
        expectMsgType[DirectorReferenceState].reference.size == 4
      }

      awaitAssert(pollForFirstState(), 5 seconds, 1 seconds)

      killActors(director)

      val resurrection = newDirector("4")

      def pollForResState(): Boolean = {
        println("Polling..")
        resurrection ! GetState()
        expectMsgType[DirectorReferenceState].reference.size == 4
      }

      awaitAssert(pollForResState(), 5 seconds, 1 seconds)
    }
  }

  "director state is persisted as is transient to its children" in {
    val director = newDirector("director")

    director ! CreateAssembleGroup("test-host-id", "test-name", "test-category")
    val id1 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id2", "test-name2", "test-category")
    val id2 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id3", "test-name3", "test-category")
    val id3 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id4", "test-name4", "test-category")
    val id4 = expectMsgType[GroupCreated].groupId

    def pollForState(): Boolean = {
      println("Polling..")
      director ! GetState()
      expectMsgType[DirectorReferenceState].reference.size == 4
    }

    awaitAssert(pollForState(), 5 seconds, 1 seconds)
    Thread.sleep(500)


    killActors(director)

    val resurrection = newDirector("director")

    def pollForResState(): Boolean = {
      println("Polling..")
      resurrection ! GetState()
      val ref = expectMsgType[DirectorReferenceState].reference
      val size = ref.size
      println(s"Got Size: $size")
      size == 4
    }

    awaitCond(pollForResState(), 5 seconds, 1 seconds)
    resurrection ! FindAssembleGroup(id1)
    //    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id1, "test-name", "test-category")))
    //
    //    resurrection ! FindAssembleGroup(id2)
    //    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id2, "test-name2", "test-category")))
    //
    //    resurrection ! FindAssembleGroup(id3)
    //    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id3, "test-name3", "test-category")))
    //
    //    resurrection ! FindAssembleGroup(id4)
    //    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id4, "test-name4", "test-category")))


  }

  private def newDirector(id: String) = {
    system.actorOf(Props(new AssembleGroupDirector(id)))
  }

}