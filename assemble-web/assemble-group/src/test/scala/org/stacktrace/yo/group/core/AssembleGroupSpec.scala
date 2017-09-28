package org.stacktrace.yo.group.core

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import com.stacktrace.yo.assemble.group.GroupProtocol.Created
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.group.AssembleGroup
import org.stacktrace.yo.group.core.group.GroupProtocol.Group.GroupReady

class AssembleGroupSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "A Group Actor" must {

    "send back a ready message with it is initialized" in {
      val testProbe = TestProbe()
      val groupActor = system.actorOf(Props(new AssembleGroup(testProbe.ref)))
      groupActor ! Created("test-actor-name")
      expectMsg(GroupReady())
    }

  }
}