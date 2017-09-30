package org.stacktrace.yo.group.core.group.director

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupCreated, GroupRetrieved}
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector.GroupCreatedRef

class AssembleGroupDirectorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupDirector Actor" must {

    "create a group" in {
      val director = system.actorOf(Props[AssembleGroupDirector])
      director ! CreateAssembleGroup("test-group-name", "test-user-id")
      val message = expectMsgType[GroupCreated] //sender gets a group created back
    }

    "retrieve a group that was created" in {
      val director = system.actorOf(Props[AssembleGroupDirector])
      val probe = TestProbe()

      director ! GroupCreatedRef("test-group-id", probe.ref)
      director ! FindAssembleGroup("test-group-id")

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Some(GroupRetrieved("test-group-id"))
    }

  }
}