package org.stacktrace.yo.user.auth.service

import java.util.UUID

import org.stacktrace.yo.user.auth.model.{AssembleUser, AuthToken}

import scala.concurrent.Future
import scala.concurrent.duration.{FiniteDuration, _}
import scala.language.postfixOps

trait AuthTokenService {

  /**
    * Creates a new auth token and saves it in the backing store.
    *
    * @param userID     The user ID for which the token should be created.
    * @param expiration The duration a token expires.
    * @return The saved auth token.
    */
  def create(userID: String, expiration: FiniteDuration = 5 minutes): Future[(UUID, AuthToken)]

  /**
    * Validates a token ID.
    *
    * @param id The token ID to validate.
    * @return The token if it's valid, None otherwise.
    */
  def validate(id: UUID): Future[Option[AuthToken]]

  /**
    * Cleans expired tokens.
    *
    * @return The list of deleted tokens.
    */
  def clean: Future[Seq[AuthToken]]

  /**
    * Finds a user from an auth token.
    *
    * @return The User
    */
  def findUserFromAuthToken(id: UUID): Future[Option[AssembleUser]]


}
