package org.stacktrace.yo.group

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.core.api.AssembleGroupAPIProtocol.CreateAssembleGroup
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated
import org.stacktrace.yo.group.core.group.AssembleGroupProtocol.Creation.CreateGroup
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector

class AssembleGroupDirectorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupDirector Actor" must {

    "creates a group and gets back a reference to it" in {
      val director = TestActorRef(new AssembleGroupDirector())
      director ! CreateAssembleGroup()
      val message = expectMsgType[GroupCreated] //sender gets a group created back
      director.underlyingActor.groupRefs.get(message.groupName).isDefined mustBe true //director has ref to the group
    }

  }
}