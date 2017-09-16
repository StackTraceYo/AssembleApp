package org.stacktrace.yo.user.auth.store.impl

import java.time.Instant
import java.util.UUID

import org.stacktrace.yo.user.auth.model.AuthToken
import org.stacktrace.yo.user.auth.store.AuthTokenStore

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class AssembleAuthTokenStore(implicit ec : ExecutionContext) extends AuthTokenStore {

  val tokens: mutable.HashMap[UUID, AuthToken] = mutable.HashMap[UUID, AuthToken]()

  /**
    * Finds a token by its ID.
    *
    * @param id The unique token ID.
    * @return The found token or None if no token for the given ID could be found.
    */
  override def find(id: UUID): Future[Option[AuthToken]] = {
    Future {
      tokens.get(id)
    }
  }

  /**
    * Finds expired tokens, and their associated UUIDS
    *
    * @param dateTime The current instant.
    */
  override def findExpired(dateTime: Instant): Future[Seq[(UUID, AuthToken)]] = {
    Future {
      tokens
        .filter(kv => {
          kv._2.expiry.isBefore(dateTime)
        })
        .toSeq
    }
  }

  /**
    * Saves a token.
    *
    * @param token The token to save.
    * @return The saved token and the UUIDS for it.
    */
  override def save(token: AuthToken): Future[(UUID, AuthToken)] = {
    Future {
      tokens.put(token.id, token)
      (token.id, token)
    }
  }

  /**
    * Removes the token for the given ID.
    *
    * @param id The ID for which the token should be removed.
    * @return A future to wait for the process to be completed.
    */
  override def remove(id: UUID): Future[Unit] = {
    Future {
      tokens.remove(key = id)
    }
  }
}
