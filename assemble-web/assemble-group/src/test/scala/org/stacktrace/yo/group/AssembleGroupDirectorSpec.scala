package org.stacktrace.yo.group

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.MustMatchers._
import org.scalatest.WordSpecLike
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.{CreateGroup, GroupCreated}
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector

class AssembleGroupDirectorSpec extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike {

  "An AssembleGroupDirector Actor" must {

    "creates a group and gets back a reference to it" in {
      val director = TestActorRef(new AssembleGroupDirector())
      director ! CreateGroup()
      val message = expectMsgType[GroupCreated] //sender gets a group created back
      director.underlyingActor.groupRefs.get(message.groupName).isDefined mustBe true //director has ref to the group
    }

  }
}