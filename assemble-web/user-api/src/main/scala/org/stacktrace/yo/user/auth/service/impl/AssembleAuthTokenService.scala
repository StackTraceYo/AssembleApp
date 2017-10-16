package org.stacktrace.yo.user.auth.service.impl

import java.time.Instant
import java.util.UUID
import javax.inject.{Inject, Singleton}

import org.stacktrace.yo.user.auth.model.AuthToken
import org.stacktrace.yo.user.auth.service.AuthTokenService
import org.stacktrace.yo.user.auth.store.{AuthTokenStore, UserStore}
import org.stacktrace.yo.user.model.AssembleUser

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AssembleAuthTokenService @Inject()(tokenStore: AuthTokenStore, userStore: UserStore)(implicit ec: ExecutionContext) extends AuthTokenService {
  /**
    * Creates a new auth token and saves it in the backing store.
    *
    * @param userID     The user ID for which the token should be created.
    * @param expiration The duration a token expires.
    * @return The saved auth token.
    */
  override def create(userID: String, expiration: FiniteDuration = 5 days): Future[(UUID, AuthToken)] = {
    tokenStore.save(
      AuthToken(
        UUID.randomUUID(),
        userID,
        Instant.now.plusSeconds(expiration.toSeconds)

      )
    )
  }

  /**
    * Validates a token ID.
    *
    * @param id The token ID to validate.
    * @return The token if it's valid, None otherwise.
    */
  override def validate(id: UUID): Future[Option[AuthToken]] = {
    tokenStore.find(id)
  }

  /**
    * Cleans expired tokens.
    *
    * @return The list of deleted tokens.
    */
  override def clean: Future[Seq[AuthToken]] = {
    tokenStore.findExpired(Instant.now())
      .map(expired => {
        expired.map(tokenPair => {
          tokenStore.remove(tokenPair._1)
          tokenPair._2
        })
      })
  }

  /**
    * Finds a user from an auth token.
    *
    * @return The User
    */
  override def findUserFromAuthToken(id: UUID): Future[Option[AssembleUser]] = {
    tokenStore.find(id)
      .flatMap {
        case Some(token) =>
          userStore.find(token.userID)
        case None =>
          Future {
            Option.empty
          }
      }

  }
}
