package org.stacktrace.yo.group.core.group.supervisor

import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestProbe}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroup, GetState, GroupCreatedFor}
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor.CreateGroupAndReturnTo

import scala.language.postfixOps

class AssembleGroupSupervisorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupSupervisor Actor" must {


    "creates a group" in {
      val director = TestActorRef(new AssembleGroupDirector("create-a-group"))
      val respondTo = TestProbe("api")
      val id = UUID.randomUUID().toString
      val hostid = UUID.randomUUID().toString

      val supervisor = TestActorRef(new AssembleGroupSupervisor(director, id))
      supervisor ! CreateGroupAndReturnTo(CreateGroup(hostid, "group-name", "category"), respondTo.ref)

      respondTo.expectMsgType[GroupCreated] //sender gets a group created back

      supervisor.getSingleChild("group-" + id) ! GetState()

      val state = expectMsgType[AssembleGroupState]

      state.groupid should equal(id)
      state.hostid should equal(hostid)
    }


    "gives director reference to it" in {
      val director = TestProbe()
      val respondTo = TestProbe()
      val id = UUID.randomUUID().toString
      val hostid = UUID.randomUUID().toString

      val supervisor = TestActorRef(new AssembleGroupSupervisor(director.ref, id))
      supervisor ! CreateGroupAndReturnTo(CreateGroup(hostid, "group-name", "category"), respondTo.ref)

      director.expectMsgType[GroupCreatedFor] //director gets a reference
    }
  }
}