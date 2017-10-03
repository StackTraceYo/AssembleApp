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
      director ! CreateAssembleGroup("test-group-name", "test-user-id")
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

    director ! CreateAssembleGroup("test-id", "test-name")
    director ! CreateAssembleGroup("test-id2", "test-name2")
    director ! CreateAssembleGroup("test-id3", "test-name3")
    director ! CreateAssembleGroup("test-id4", "test-name4")
    receiveN(4, 1 seconds)

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
      expectMsgType[DirectorReferenceState].reference.size == 4
    }

    awaitCond(pollForResState(), 5 seconds, 1 seconds)


  }

  private def newDirector(id: String) = {
    system.actorOf(Props(new AssembleGroupDirector(id)))
  }

}