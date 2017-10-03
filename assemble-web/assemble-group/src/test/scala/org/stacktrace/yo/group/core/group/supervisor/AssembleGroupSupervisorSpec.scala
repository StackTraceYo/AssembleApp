package org.stacktrace.yo.group.core.group.supervisor

import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestProbe}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GetState, GroupCreatedRef}
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated

class AssembleGroupSupervisorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupSupervisor Actor" must {


    "creates a group" in {
      val director = TestProbe()
      val id = UUID.randomUUID().toString
      val hostid = UUID.randomUUID().toString

      val supervisor = TestActorRef(new AssembleGroupSupervisor(director.ref, id))
      supervisor ! CreateGroup(hostid)

      expectMsgType[GroupCreated] //sender gets a group created back

      supervisor.getSingleChild("group-" + id) ! GetState()

      val state = expectMsgType[AssembleGroupState]

      state.groupid should equal(id)
      state.hostid should equal(hostid)
    }


    "gives director reference to it" in {
      val director = TestProbe()
      val id = UUID.randomUUID().toString
      val hostid = UUID.randomUUID().toString

      val supervisor = TestActorRef(new AssembleGroupSupervisor(director.ref, id))
      supervisor ! CreateGroup(hostid)

      expectMsgType[GroupCreated] //sender gets a group created back
      director.expectMsgType[GroupCreatedRef] //director gets a reference
    }
  }
}