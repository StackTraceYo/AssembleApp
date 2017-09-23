package org.stacktrace.yo.group.handler

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import org.scalatest.WordSpecLike
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.{CreateGroup, GroupCreated}

class GroupResponseHandlerSpec extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike {

  "An GroupResponseHandler Actor" must {

    "sends back a group created message" in {
      val probe = TestProbe() // requester ( rest api)
      val handler = TestActorRef(new GroupAPIResponseHandler(probe.ref))
      handler ! GroupCreated("group created yay")
      probe.expectMsg(GroupCreated("group created yay"))
    }

  }
}