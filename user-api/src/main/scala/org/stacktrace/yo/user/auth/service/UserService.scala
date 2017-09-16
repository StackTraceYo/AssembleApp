package org.stacktrace.yo.user.auth.service

import org.stacktrace.yo.user.auth.model.AssembleUser

import scala.concurrent.Future

trait UserService {

  /**
    * Retrieves a user that matches the specified ID.
    *
    * @param id The ID to retrieve a user.
    * @return The retrieved user or None if no user could be retrieved for the given ID.
    */
  def retrieve(id: String): Future[Option[AssembleUser]]

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user.
    */
  def save(user: AssembleUser): Future[AssembleUser]
}
