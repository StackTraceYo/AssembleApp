package org.stacktrace.yo.user.auth.service

import org.scalatestplus.play.PlaySpec
import org.stacktrace.yo.user.auth.model.AssembleUser
import org.stacktrace.yo.user.auth.service.impl.AssembleUserService
import org.stacktrace.yo.user.auth.store.impl.AssembleUserStore

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleUserServiceSpec extends PlaySpec {

  "AssembleUserService" should {

    "save a user" in new Context {

      val user: AssembleUser = AssembleUser("testid", Option("Ahmad"), Option("Email"))
      Await.result(service.save(user), 5 seconds) mustBe user

    }

    "retrieve a user" in new Context {
      val user: AssembleUser = AssembleUser("testid", Option("Ahmad"), Option("Email"))
      val user1: AssembleUser = AssembleUser("testid1", Option("Ahmad1"), Option("Email1"))
      val user2: AssembleUser = AssembleUser("testid2", Option("Ahmad2"), Option("Email2"))
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
