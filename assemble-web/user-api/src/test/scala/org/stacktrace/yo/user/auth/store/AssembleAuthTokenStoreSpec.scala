package org.stacktrace.yo.user.auth.store

import java.time.Instant
import java.util.UUID

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.stacktrace.yo.user.auth.model.AuthToken
import org.stacktrace.yo.user.auth.store.impl.AssembleAuthTokenStore

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleAuthTokenStoreSpec extends PlaySpec with GuiceOneAppPerSuite {

  "AssembleAuthTokenStore" should {

    "store a token" in new Context {
      val id: UUID = UUID.randomUUID()
      val token = AuthToken(id, "testuserid", Instant.now().plusSeconds(1))

      Await.result(store.save(token), 5 seconds) mustBe(id, token)
    }

    "find a token" in new Context {
      val id: UUID = UUID.randomUUID()
      val token = AuthToken(id, "testuserid", Instant.now().plusSeconds(1))
      Await.result(store.save(token), 5 seconds)
      Await.result(store.find(id), 5 seconds).get mustBe token
    }

    "remove a token" in new Context {
      val id: UUID = UUID.randomUUID()
      val token = AuthToken(id, "testuserid", Instant.now().plusSeconds(1))
      Await.result(store.save(token), 5 seconds) mustBe(id, token)
      Await.result(store.remove(id), 5 seconds)

      Await.result(store.find(id), 5 seconds) mustBe Option.empty

    }

    "return an empty option when no token is found" in new Context {

      Await.result(store.find(UUID.randomUUID()), 5 seconds) mustBe Option.empty
    }

    "find expired tokens" in new Context {
      val id: UUID = UUID.randomUUID()
      val token = AuthToken(id, "testuserid", Instant.now())
      Await.result(store.save(token), 5 seconds)
      val expired: Seq[(UUID, AuthToken)] = Await.result(store.findExpired(Instant.now.plusSeconds(5)), 5 seconds)

      expired.size mustBe 1
      expired.head mustBe(id, token)
    }

  }

  trait Context extends {

    implicit val executionContext: ExecutionContextExecutor = ExecutionContext.Implicits.global

    val store = new AssembleAuthTokenStore()
  }

}
