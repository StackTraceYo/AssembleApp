package org.stacktrace.yo

import java.util.UUID

import akka.actor.{Actor, ActorContext, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by Stacktraceyo on 9/29/17.
  */
object TestActors {

  abstract class TestContext() extends TestKit(ActorSystem(UUID.randomUUID().toString)) with ImplicitSender {

    implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  }

  class TestGroupActor extends Actor {

    def receive: Receive = {
      case _ =>
        sender() ! AssembleGroupState("host", UUID.randomUUID().toString)

    }


  }

  class AvailableContextActor(childName: String) extends Actor {

    val child: ActorRef = context.actorOf(Props(new TestGroupActor), s"assemble-group-supervisor-$childName")

    def getContext: ActorContext = {
      context
    }

    def receive: Receive = {
      case _ =>
        sender() ! AssembleGroupState()

    }


  }

  class AvailableEmptyContextActor() extends Actor {

    def getContext: ActorContext = {
      context
    }

    def receive: Receive = {
      case _ =>
        sender() ! AssembleGroupState()

    }


  }


}
