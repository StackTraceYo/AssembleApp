package org.stacktrace.yo.user.auth.store

import org.scalatest.Matchers._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.stacktrace.yo.user.auth.store.impl.AssembleUserStore
import org.stacktrace.yo.user.model.AssembleUser

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleUserStoreSpec extends PlaySpec with GuiceOneAppPerSuite {

  "AssembleUserStore" should {

    "store a new user" in new Context {
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(store.save(user), 5 seconds) mustBe user
    }

    "not save a duplicate user with same id" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(store.save(user), 5 seconds)

      ScalaFutures.whenReady(store.save(user).failed) { s =>
        s shouldBe a[RuntimeException]
      }
    }

    "not save a duplicate user with same email" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val user2: AssembleUser = AssembleUser("testid2", "Email", Option("Ahmad"))
      Await.result(store.save(user), 5 seconds)

      ScalaFutures.whenReady(store.save(user2).failed) { s =>
        s shouldBe a[RuntimeException]
      }
    }

    "find user" in new Context {
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val user2: AssembleUser = AssembleUser("testid2", "Email2", Option("Ahmad2"))
      val user3: AssembleUser = AssembleUser("testid3", "Email3", Option("Ahmad3"))
      store.save(user)
      store.save(user2)
      store.save(user3)

      Await.result(store.find("testid"), 1 second).get mustBe user
      Await.result(store.find("testid2"), 1 second).get mustBe user2
      Await.result(store.find("testid3"), 1 second).get mustBe user3
    }

    "return an empty option when there is no user" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      store.save(user)
      Await.result(store.find("nonexistant"), 1 second) mustBe Option.empty
    }
  }

  trait Context extends {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global

    val store = new AssembleUserStore()

  }

}
