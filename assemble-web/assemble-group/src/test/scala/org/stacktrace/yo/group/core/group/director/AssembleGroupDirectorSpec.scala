package org.stacktrace.yo.group.core.group.director

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, GroupCreated}

class AssembleGroupDirectorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupDirector Actor" must {

    "creates a group and gets back a reference to it" in {
      val director = TestActorRef(new AssembleGroupDirector())
      director ! CreateAssembleGroup("test-group-name", "test-user-id")
      val message = expectMsgType[GroupCreated] //sender gets a group created back
      director.underlyingActor.groupRefs.get(message.groupId).isDefined mustBe true //director has ref to the group
    }

  }
}