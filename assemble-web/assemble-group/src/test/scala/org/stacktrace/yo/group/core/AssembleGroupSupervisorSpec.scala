package org.stacktrace.yo.group.core

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestProbe}
import com.stacktrace.yo.assemble.group.GroupProtocol.Created
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated
import org.stacktrace.yo.group.core.group.AssembleGroup
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.CreateGroup
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Group.GroupReady
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef
import org.stacktrace.yo.group.core.group.supervisor.AssembleGroupSupervisor

class AssembleGroupSupervisorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupSupervisor Actor" must {

    "sends back a group created message" in {
      val director = TestProbe()
      val actorRef = system.actorOf(Props(new AssembleGroupSupervisor(director.ref)))
      actorRef ! CreateGroup("testactorname")
      Thread.sleep(5000)
      expectMsg(GroupCreated("testactorname")) //sender gets the name
      val message = director.expectMsgType[GroupCreatedRef] //director gets the name and ref
      message.groupName mustBe "testactorname"
    }

  }

//  val testProbe = TestProbe()
//  val groupActor = system.actorOf(Props(new AssembleGroup(testProbe.ref)))
//  groupActor ! Created("test-actor-name")
//  expectMsg(GroupReady())
}