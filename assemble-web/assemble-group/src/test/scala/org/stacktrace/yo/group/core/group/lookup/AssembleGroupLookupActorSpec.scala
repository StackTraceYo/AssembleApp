package org.stacktrace.yo.group.core.group.lookup

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestProbe
import org.scalatest.MustMatchers._
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.GroupRetrieved
import org.stacktrace.yo.group.core.group.lookup.AssembleLookupActor.LookupGroup

class AssembleGroupLookupActorSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An AssembleGroupLookup Actor" must {

    "retrieve a group that was created" in {
      val probe1 = TestProbe()
      val probe2 = TestProbe()
      val probe3 = TestProbe()

      val ref = Map(
        "test-1" -> probe1.ref,
        "test-2" -> probe2.ref,
        "test-3" -> probe3.ref
      )
      val lookup = system.actorOf(Props(new AssembleLookupActor(ref, "test-1", "")))

      lookup ! LookupGroup()

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Some(GroupRetrieved(AssembledGroup("test-1")))
    }

    "return an empty option if no group exists" in {
      val probe1 = TestProbe()
      val probe2 = TestProbe()
      val probe3 = TestProbe()

      val ref = Map(
        "test-1" -> probe1.ref,
        "test-2" -> probe2.ref,
        "test-3" -> probe3.ref
      )
      val lookup = system.actorOf(Props(new AssembleLookupActor(ref, "fail-name", "")))

      lookup ! LookupGroup()

      val message2 = expectMsgType[Option[GroupRetrieved]]
      message2 mustBe Option.empty
    }


  }
}