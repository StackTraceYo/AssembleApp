package org.stacktrace.yo.group.core

import java.util.UUID

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.Protocol.CreateGroup
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupCreated
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor

class AssembleGroupSupervisorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupSupervisor Actor" must {

    "sends back a group created message" in {
      val director = TestProbe()
      val actorRef = system.actorOf(Props(new AssembleGroupSupervisor(director.ref)))
      val id = UUID.randomUUID().toString
      actorRef ! CreateGroup(id, "test-group-name")
      expectMsg(GroupCreated(id)) //sender gets the name
      val message = director.expectMsgType[GroupCreatedRef] //director gets the name and ref
      message.groupName mustBe id
    }
  }
}