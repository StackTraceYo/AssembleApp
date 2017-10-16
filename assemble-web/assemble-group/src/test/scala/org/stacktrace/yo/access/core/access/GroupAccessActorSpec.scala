package org.stacktrace.yo.access.core.access

import akka.actor.{ActorRef, PoisonPill, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.access.AccessProtocol.GroupAccessState
import com.stacktrace.yo.assemble.group.Protocol.{CreateGroupAccess, GetState}
import org.scalatest.{Matchers, WordSpecLike}
import org.stacktrace.yo.TestActors.TestContext

import scala.language.postfixOps

class GroupAccessActorSpec extends WordSpecLike with Matchers {


  "A GroupAccessActor" must {

    "store and retrieve newly created groups" in new TestContext() {
      val director = TestProbe()
      val actorTest: ActorRef = system.actorOf(Props(new GroupAccessActor(director.ref)))
      actorTest ! CreateGroupAccess("hostId", "123")
      actorTest ! CreateGroupAccess("hostId", "123")
      actorTest ! CreateGroupAccess("hostId", "1234")
      actorTest ! CreateGroupAccess("hostId2", "12345")
      actorTest ! GetState()
      val message: GroupAccessState = expectMsgType[GroupAccessState] //sender gets a group created back
      message.groups.size shouldBe 2
      message.groups("hostId").host.size shouldBe 2
      message.groups("hostId2").host.size shouldBe 1
      message.groups("hostId2").host.head shouldBe "12345"
    }

    "reload persisted groups" in new TestContext() {
      val director = TestProbe()
      val actorTest: ActorRef = system.actorOf(Props(new GroupAccessActor(director.ref)))
      actorTest ! CreateGroupAccess("hostId", "123")
      actorTest ! CreateGroupAccess("hostId2", "12345")
      actorTest ! GetState()
      val message: GroupAccessState = expectMsgType[GroupAccessState] //sender gets a group created back
      message.groups.size shouldBe 2
      message.groups("hostId").host.head shouldBe "123"
      message.groups("hostId2").host.head shouldBe "12345"

      watch(actorTest)
      actorTest ! PoisonPill
      expectTerminated(actorTest)

      val res: ActorRef = system.actorOf(Props(new GroupAccessActor(director.ref)))
      res ! GetState()
      val resMessage: GroupAccessState = expectMsgType[GroupAccessState] //sender gets a group created back
      resMessage.groups.size shouldBe 2
      resMessage.groups("hostId").host.head shouldBe "123"
      resMessage.groups("hostId2").host.head shouldBe "12345"

    }
  }

}