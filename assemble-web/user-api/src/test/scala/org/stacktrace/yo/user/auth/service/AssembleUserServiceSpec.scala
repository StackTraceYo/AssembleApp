package org.stacktrace.yo.user.auth.service

import java.time.Instant
import java.util.UUID

import org.mockito.Mockito.when
import org.scalatest.Matchers._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.stacktrace.yo.user.auth.model.{AuthToken, LoginData}
import org.stacktrace.yo.user.auth.service.impl.AssembleUserService
import org.stacktrace.yo.user.auth.store.impl.AssembleUserStore
import org.stacktrace.yo.user.model.AssembleUser

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.language.postfixOps


class AssembleUserServiceSpec extends PlaySpec {


  "AssembleUserService" should {

    "save a user" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds) mustBe(user, token)

    }

    "not save a duplicate user with same id" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds)

      ScalaFutures.whenReady(service.save(user).failed) { s =>
        s shouldBe a[RuntimeException]
      }
    }

    "not save a duplicate user with same email" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val user2: AssembleUser = AssembleUser("testid2", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds)

      ScalaFutures.whenReady(service.save(user2).failed) { s =>
        s shouldBe a[RuntimeException]
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

    "retrieve a user by email" in new Context {
      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      Await.result(service.save(user), 5 seconds)

      Await.result(service.retrieve(LoginData("Email", "")), 5 seconds) mustBe Some((user, token))

    }

    "return empty option for nonexistant user" in new Context {
      Await.result(service.retrieve("testid2"), 5 seconds) mustBe Option.empty
    }

  }


  trait Context extends MockitoSugar {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global

    val store: AssembleUserStore = new AssembleUserStore()
    val authService: AuthTokenService = mock[AuthTokenService]

    val token: AuthToken = AuthToken(UUID.randomUUID(), "mockUserId", Instant.now())

    val service: AssembleUserService = new AssembleUserService(store, authService)
    setup()

    def setup(): Unit = {
      when(authService.create("testid")).thenReturn(
        Future {
          (token.id, token)
        }
      )
      when(authService.create("testid1")).thenReturn(
        Future {
          (token.id, token)
        }
      )
      when(authService.create("testid2")).thenReturn(
        Future {
          (token.id, token)
        }
      )
    }

  }

}
