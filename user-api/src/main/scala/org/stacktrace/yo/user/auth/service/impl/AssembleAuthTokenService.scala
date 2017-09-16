package org.stacktrace.yo.user.auth.service.impl

import java.time.Instant
import java.util.UUID
import javax.inject.Inject

import org.stacktrace.yo.user.auth.model.AuthToken
import org.stacktrace.yo.user.auth.service.AuthTokenService
import org.stacktrace.yo.user.auth.store.AuthTokenStore

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class AssembleAuthTokenService @Inject()(tokenStore: AuthTokenStore)(implicit ec: ExecutionContext) extends AuthTokenService {
  /**
    * Creates a new auth token and saves it in the backing store.
    *
    * @param userID     The user ID for which the token should be created.
    * @param expiration The duration a token expires.
    * @return The saved auth token.
    */
  override def create(userID: String, expiration: FiniteDuration = 5 minutes): Future[(UUID, AuthToken)] = {
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
}
