package org.stacktrace.yo.group

import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.Config
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.stacktrace.yo.PersistenceCleanup

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by Stacktraceyo on 9/27/17.
  */
abstract class AssemblePersistenceSpec(system: ActorSystem) extends TestKit(system)
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with PersistenceCleanup {

  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  def this(name: String, config: Config) = this(ActorSystem(name, config))

  override protected def beforeAll() = deleteStorageLocations()

  override protected def afterAll() = {
    deleteStorageLocations()
    TestKit.shutdownActorSystem(system)
  }

  def killActors(actors: ActorRef*) = {
    actors.foreach { actor =>
      watch(actor)
      actor ! PoisonPill
      expectTerminated(actor)
    }
  }
}