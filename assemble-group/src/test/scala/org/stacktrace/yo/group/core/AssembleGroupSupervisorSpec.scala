package org.stacktrace.yo.group.core

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import org.scalatest.MustMatchers._
import org.scalatest.WordSpecLike
import org.stacktrace.yo.group.core.AssembleProtocol.Creation.{CreateGroup, GroupCreated, GroupCreatedRef}

class AssembleGroupSupervisorSpec extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike {

  "An AssembleGroupSupervisor Actor" must {

    "sends back a group created message" in {
      val director = TestProbe()
      val actorRef = TestActorRef(new AssembleGroupSupervisor(director.ref, "testactorname"))
      actorRef ! CreateGroup()

      expectMsg(GroupCreated("testactorname")) //sender gets the name
      val message = director.expectMsgType[GroupCreatedRef] //director gets the name and ref
      message.groupName mustBe "testactorname"
      actorRef.underlyingActor.ready mustBe true //set group to ready
    }

  }
}