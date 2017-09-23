package org.stacktrace.yo.user.auth.store

import java.time.Instant
import java.util.UUID

import org.stacktrace.yo.user.auth.model.AuthToken

import scala.concurrent.Future

trait AuthTokenStore {

  /**
    * Finds a token by its ID.
    *
    * @param id The unique token ID.
    * @return The found token or None if no token for the given ID could be found.
    */
  def find(id: UUID): Future[Option[AuthToken]]

  /**
    * Finds expired tokens.
    *
    * @param dateTime The current instant.
    */
  def findExpired(dateTime: Instant): Future[Seq[(UUID, AuthToken)]]

  /**
    * Saves a token.
    *
    * @param token The token to save.
    * @return The saved token.
    */
  def save(token: AuthToken): Future[(UUID, AuthToken)]

  /**
    * Removes the token for the given ID.
    *
    * @param id The ID for which the token should be removed.
    * @return A future to wait for the process to be completed.
    */
  def remove(id: UUID): Future[Unit]
}