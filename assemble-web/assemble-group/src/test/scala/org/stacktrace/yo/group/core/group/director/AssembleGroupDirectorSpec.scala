package org.stacktrace.yo.group.core.group.director

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.GroupProtocol.DirectorReferenceState
import com.stacktrace.yo.assemble.group.Protocol.GetState
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupCreated, GroupRetrieved}
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

import scala.concurrent.duration._
import scala.language.postfixOps

class AssembleGroupDirectorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {


  "An AssembleGroupDirector Actor" must {

    "create a group" in {
      val director = newDirector("1")
      director ! CreateAssembleGroup("test-user-id", "test-group-name")
      val message = expectMsgType[GroupCreated] //sender gets a group created back
    }

    "retrieve a group that was created" in {
      val director = newDirector("2")
      val probe = TestProbe()

      director ! GroupCreatedRef("test-group-id", probe.ref)
      director ! FindAssembleGroup("test-group-id")

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Some(GroupRetrieved(AssembledGroup("test-group-id")))
    }

    "return an empty option when no group is found" in {
      val director = newDirector("3")
      director ! FindAssembleGroup("test-group-id2")

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Option.empty
    }

    "director state is persisted" in {
      val director = newDirector("4")
      director ! GroupCreatedRef("test-group-id", TestProbe().ref)
      director ! GroupCreatedRef("test-group-id2", TestProbe().ref)
      director ! GroupCreatedRef("test-group-id3", TestProbe().ref)
      director ! GroupCreatedRef("test-group-id4", TestProbe().ref)
      director ! GetState()
      val message1 = expectMsgType[DirectorReferenceState]
      message1.reference.size shouldBe 4

      killActors(director)

      val resurrection = newDirector("4")


      def pollForState(): Boolean = {
        println("Polling..")
        resurrection ! GetState()
        expectMsgType[DirectorReferenceState].reference.size == 4
      }

      awaitAssert(pollForState(), 5 seconds, 1 seconds)
    }
  }

  "director state is persisted as is transient to its children" in {
    val director = newDirector("5")

    director ! CreateAssembleGroup("test-host-id", "test-name")
    val id1 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id2", "test-name2")
    val id2 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id3", "test-name3")
    val id3 = expectMsgType[GroupCreated].groupId
    director ! CreateAssembleGroup("test-host-id4", "test-name4")
    val id4 = expectMsgType[GroupCreated].groupId

    def pollForState(): Boolean = {
      println("Polling..")
      director ! GetState()
      expectMsgType[DirectorReferenceState].reference.size == 4
    }

    awaitAssert(pollForState(), 5 seconds, 1 seconds)


    killActors(director)

    val resurrection = newDirector("5")

    def pollForResState(): Boolean = {
      println("Polling..")
      resurrection ! GetState()
      val size = expectMsgType[DirectorReferenceState].reference.size
      println(s"Got Size: $size")
      size == 4
    }

    awaitCond(pollForResState(), 5 seconds, 1 seconds)
    resurrection ! FindAssembleGroup(id1)
    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id1)))

    resurrection ! FindAssembleGroup(id2)
    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id2)))

    resurrection ! FindAssembleGroup(id3)
    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id3)))

    resurrection ! FindAssembleGroup(id4)
    expectMsgType[Option[GroupRetrieved]] mustBe Some(GroupRetrieved(AssembledGroup(id4)))


  }

  private def newDirector(id: String) = {
    system.actorOf(Props(new AssembleGroupDirector(id)))
  }

}