package org.stacktrace.yo.group.core

import java.util.UUID

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GetState}
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated
import org.stacktrace.yo.group.core.group.core.AssembleGroupActor
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

class AssembleGroupActorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupSupervisor Actor" must {

    "sends back a group created message" in {
      val director = TestProbe()
      val id = UUID.randomUUID().toString
      val hostId = UUID.randomUUID().toString
      val actorRef = system.actorOf(Props(new AssembleGroupActor(director.ref, id)))
      actorRef ! CreateGroup(hostId)
      expectMsg(GroupCreated(id)) //sender gets the name
      val message = director.expectMsgType[GroupCreatedRef] //director gets the name and ref
      message.groupName mustBe id
    }

    "supervisor state is persisted" in {
      val director = TestProbe()
      val id = UUID.randomUUID().toString
      val hostId = UUID.randomUUID().toString
      val supervisor = system.actorOf(Props(new AssembleGroupActor(director.ref, id)))
      supervisor ! CreateGroup(hostId)
      expectMsg(GroupCreated(id)) //sender gets the name
      val message = director.expectMsgType[GroupCreatedRef] //director gets the name and ref

      killActors(supervisor)

      var resurrection = system.actorOf(Props(new AssembleGroupActor(director.ref, id)))
      resurrection ! GetState()
      var state = expectMsgType[AssembleGroupState]
      state.groupid mustBe id
      state.hostid mustBe hostId

      killActors(supervisor)

      resurrection = system.actorOf(Props(new AssembleGroupActor(director.ref, id)))
      resurrection ! GetState()
      state = expectMsgType[AssembleGroupState]
      state.groupid mustBe id
      state.hostid mustBe hostId
    }
  }
}