package org.stacktrace.yo.user.auth.service

import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.stacktrace.yo.user.auth.model.AssembleUser
import org.stacktrace.yo.user.auth.service.impl.AssembleUserService
import org.stacktrace.yo.user.auth.store.impl.AssembleUserStore

import scala.concurrent.duration._
import org.scalatest.Matchers._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleUserServiceSpec extends PlaySpec {

  "AssembleUserService" should {

    "save a user" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds) mustBe user

    }

    "not save a duplicate user with same id" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds)

      ScalaFutures.whenReady(service.save(user).failed) { s =>
        s shouldBe a [RuntimeException]
      }
    }

    "not save a duplicate user with same email" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val user2: AssembleUser = AssembleUser("testid2", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds)

      ScalaFutures.whenReady(service.save(user2).failed) { s =>
        s shouldBe a [RuntimeException]
      }
    }

    "retrieve a user" in new Context {
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val user1: AssembleUser = AssembleUser("testid1", "Emai2", Option("Ahmad1"))
      val user2: AssembleUser = AssembleUser("testid2", "Emai3", Option("Ahmad2"))
      Await.result(service.save(user), 5 seconds)
      Await.result(service.save(user1), 5 seconds)
      Await.result(service.save(user2), 5 seconds)

      Await.result(service.retrieve("testid2"), 5 seconds) mustBe Some(user2)

    }

    "return empty option for nonexistant user" in new Context {
      Await.result(service.retrieve("testid2"), 5 seconds) mustBe Option.empty
    }

  }

  trait Context {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global

    val store: AssembleUserStore = new AssembleUserStore()

    val service: AssembleUserService = new AssembleUserService(store)

  }

}
