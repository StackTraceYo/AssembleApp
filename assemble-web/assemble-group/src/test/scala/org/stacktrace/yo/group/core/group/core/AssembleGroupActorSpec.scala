package org.stacktrace.yo.group.core.group.core

import java.util.UUID

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.{CreateDetails, CreateGroup, GetState}
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor.GroupReady

class AssembleGroupActorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroup Actor" must {

    "sends back a group created message" in {
      val supervisor = TestProbe()
      val id = UUID.randomUUID().toString
      val hostId = UUID.randomUUID().toString

      val actorRef = system.actorOf(Props(new AssembleGroupActor(supervisor.ref, id)))
      actorRef ! CreateGroup(hostId, "name", "category", CreateDetails(2))

      val message = supervisor.expectMsg(GroupReady(id)) //sender gets the name
      message.id mustBe id
    }

    "supervisor state is persisted" in {
      val supervisor = TestProbe()
      val id = UUID.randomUUID().toString
      val hostId = UUID.randomUUID().toString
      val actorUnderTest = system.actorOf(Props(new AssembleGroupActor(supervisor.ref, id)))
      actorUnderTest ! CreateGroup(hostId, "name", "category", CreateDetails(2))
      supervisor.expectMsg(GroupReady(id)) //sender gets the name

      killActors(actorUnderTest)

      var resurrection = system.actorOf(Props(new AssembleGroupActor(supervisor.ref, id)))
      resurrection ! GetState()
      var state = expectMsgType[AssembleGroupState]
      state.groupid mustBe id
      state.hostid mustBe hostId

      killActors(actorUnderTest)

      resurrection = system.actorOf(Props(new AssembleGroupActor(supervisor.ref, id)))
      resurrection ! GetState()
      state = expectMsgType[AssembleGroupState]
      state.groupid mustBe id
      state.hostid mustBe hostId
    }
  }
}