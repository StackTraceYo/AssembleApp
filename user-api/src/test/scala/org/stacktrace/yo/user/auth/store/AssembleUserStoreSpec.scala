package org.stacktrace.yo.user.auth.store

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.stacktrace.yo.user.auth.model.AssembleUser
import org.stacktrace.yo.user.auth.store.impl.AssembleUserStore

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleUserStoreSpec extends PlaySpec with GuiceOneAppPerSuite {

  "AssembleUserStore" should {

    "store a new user" in new Context {
      val user = AssembleUser("testid", Option("Ahmad"), Option("Email"))
      Await.result(store.save(user), 5 seconds) mustBe user
    }

    "find user" in new Context {
      val user = AssembleUser("testid", Option("Ahmad"), Option("Email"))
      val user2 = AssembleUser("testid2", Option("Ahmad2"), Option("Email2"))
      val user3 = AssembleUser("testid3", Option("Ahmad3"), Option("Email3"))
      store.save(user)
      store.save(user2)
      store.save(user3)

      Await.result(store.find("testid"), 1 second).get mustBe user
      Await.result(store.find("testid2"), 1 second).get mustBe user2
      Await.result(store.find("testid3"), 1 second).get mustBe user3
    }

    "return an empty option when there is no user" in new Context {

      val user = AssembleUser("testid", Option("Ahmad"), Option("Email"))
      store.save(user)
      Await.result(store.find("nonexistant"), 1 second) mustBe Option.empty
    }
  }

  trait Context extends {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global

    val store = new AssembleUserStore()

  }

}
