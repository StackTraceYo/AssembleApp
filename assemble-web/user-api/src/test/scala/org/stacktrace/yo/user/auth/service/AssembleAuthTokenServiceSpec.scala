package org.stacktrace.yo.user.auth.service

import java.time.{Clock, Instant, ZoneId}
import java.util.UUID

import org.scalatestplus.play.PlaySpec
import org.stacktrace.yo.user.auth.model.{AssembleUser, AuthToken}
import org.stacktrace.yo.user.auth.service.impl.AssembleAuthTokenService
import org.stacktrace.yo.user.auth.store.impl.{AssembleAuthTokenStore, AssembleUserStore}
import org.stacktrace.yo.user.auth.store.{AuthTokenStore, UserStore}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleAuthTokenServiceSpec extends PlaySpec {

  "AssembleAuthTokenService" should {

    "create a new token" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val token: (UUID, AuthToken) = Await.result(service.create(user.id), 5 seconds)

      token._2.userID mustBe user.id
      //5 min = 300 seconds
      clock.instant().plusSeconds(301).isAfter(token._2.expiry) mustBe true

      Await.result(store.find(token._1), 5 seconds) mustBe Some(token._2)
    }

    "validate a new token" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val token: (UUID, AuthToken) = Await.result(service.create(user.id), 5 seconds)
      val valid = Await.result(service.validate(token._1), 5 seconds)

      Await.result(store.find(token._1), 5 seconds) mustBe Some(token._2)

      valid mustBe Some(token._2)
      valid.get.userID mustBe user.id
    }

    "cleans expired tokens" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val token: (UUID, AuthToken) = Await.result(service.create(user.id, -10 seconds), 5 seconds)
      val expired: Seq[AuthToken] = Await.result(service.clean, 1 seconds)

      expired.size mustBe 1
      expired.head mustBe token._2

      Await.result(store.find(token._1), 5 seconds) mustBe Option.empty

    }


    "expired tokens give empty option" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      val token: (UUID, AuthToken) = Await.result(service.create(user.id, -10 seconds), 5 seconds)
      Await.result(service.clean, 1 seconds)
      val valid: Option[AuthToken] = Await.result(service.validate(token._1), 5 seconds)
      valid mustBe Option.empty
    }

    "retrieve a user from a token" in new Context {

      val user: AssembleUser = AssembleUser("testid", "Email", Option("Ahmad"))
      userStore.save(user)
      val token: (UUID, AuthToken) = Await.result(service.create(user.id), 5 seconds)

      Await.result(service.findUserFromAuthToken(token._1), 5 seconds) mustBe Some(user)
    }
  }

  trait Context {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global
    val clock: Clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"))

    val store: AuthTokenStore = new AssembleAuthTokenStore()
    val userStore: UserStore = new AssembleUserStore()

    val service = new AssembleAuthTokenService(store, userStore)

  }

}
