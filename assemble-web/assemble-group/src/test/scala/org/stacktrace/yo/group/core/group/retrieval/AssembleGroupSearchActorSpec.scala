package org.stacktrace.yo.group.core.group.retrieval

import akka.actor.ActorRef
import akka.testkit.{TestActorRef, TestProbe}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState
import com.stacktrace.yo.assemble.group.Protocol.GetState
import org.scalatest.{Matchers, WordSpecLike}
import org.stacktrace.yo.TestActors.{AvailableContextActor, AvailableEmptyContextActor, TestContext, TestGroupActor}
import org.stacktrace.yo.group.core.api.GroupAPIProtocol._

import scala.language.postfixOps

class AssembleGroupSearchActorSpec extends WordSpecLike with Matchers {


  "An AssembleGroupSearch Actor" must {

    "find a group" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val reference = TestActorRef(new TestGroupActor())
      map.put("test", reference)
      val classToTest = TestActorRef(new GroupSearchActor(null, map.toMap))
      classToTest ! FindAssembleGroup("test")
      expectMsgType[Some[GroupRetrieved]]
    }

    "when find a group, asks for state" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val reference = TestProbe()
      map.put("test", reference.ref)
      val classToTest = TestActorRef(new GroupSearchActor(null, map.toMap))
      classToTest ! FindAssembleGroup("test")
      reference.expectMsgType[GetState]
    }

    "not find a group" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val context = TestActorRef(new AvailableContextActor("context"))
      val classToTest = TestActorRef(new GroupSearchActor(context.underlyingActor.getContext, map.toMap))
      classToTest ! FindAssembleGroup("test")
      expectMsg(None)
    }

    "find a group by context" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val context = TestActorRef(new AvailableContextActor("test"))
      val classToTest = TestActorRef(new GroupSearchActor(context.underlyingActor.getContext, map.toMap))
      classToTest ! FindAssembleGroup("assemble-group-supervisor-test")
      expectMsgType[Some[AssembleGroupState]]
    }

    "find groups in context" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val context = TestActorRef(new AvailableContextActor("test-list-context-1")).underlyingActor.context
      val classToTest = TestActorRef(new GroupSearchActor(context, map.toMap))
      classToTest ! ListNamedAssembleGroups(Seq("test-list-context-1"))
      val message = expectMsgType[NamedGroupsRetrieved]
      //one in context and 3 in ref
      message.hosted.size shouldBe 1
    }

    "find groups in ref map" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val reference = TestActorRef(new TestGroupActor())
      val reference2 = TestActorRef(new TestGroupActor())
      val reference3 = TestActorRef(new TestGroupActor())
      map.put(reference.underlyingActor.id, reference)
      map.put(reference2.underlyingActor.id, reference2)
      map.put(reference3.underlyingActor.id, reference3)
      val context = TestActorRef(new AvailableEmptyContextActor()).underlyingActor.context
      val classToTest = TestActorRef(new GroupSearchActor(context, map.toMap))
      classToTest ! ListNamedAssembleGroups(Seq(reference.underlyingActor.id, reference2.underlyingActor.id, reference3.underlyingActor.id))
      val message = expectMsgType[NamedGroupsRetrieved]
      //one in context and 3 in ref
      message.hosted.size shouldBe 3
    }

    "find groups in both context and ref map" in new TestContext {

      val map = scala.collection.mutable.HashMap[String, ActorRef]()
      val reference = TestActorRef(new TestGroupActor())
      val reference2 = TestActorRef(new TestGroupActor())
      val reference3 = TestActorRef(new TestGroupActor())
      map.put(reference.underlyingActor.id, reference)
      map.put(reference2.underlyingActor.id, reference2)
      map.put(reference3.underlyingActor.id, reference3)
      val context = TestActorRef(new AvailableContextActor("test-list-context-1")).underlyingActor.context
      val classToTest = TestActorRef(new GroupSearchActor(context, map.toMap))
      classToTest ! ListNamedAssembleGroups(Seq("test-list-context-1", reference.underlyingActor.id, reference2.underlyingActor.id, reference3.underlyingActor.id))
      val message = expectMsgType[NamedGroupsRetrieved]
      //one in context and 3 in ref
      message.hosted.size shouldBe 4
    }

  }
}