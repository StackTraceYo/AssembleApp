package org.stacktrace.yo.group.core

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import org.scalatest.WordSpecLike
import org.stacktrace.yo.group.core.AssembleProtocol.Group.{GroupReady, Init}

class AssembleGroupSpec extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike {

  "A Group Actor" must {

    "send back a ready message with it is initialized" in {
      val testProbe = TestProbe().ref
      val actorRef = TestActorRef(new AssembleGroup(testProbe))
      actorRef ! Init()
      expectMsg(GroupReady())
    }

  }
}