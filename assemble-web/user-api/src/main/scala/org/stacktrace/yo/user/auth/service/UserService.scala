package org.stacktrace.yo.user.auth.service

import org.stacktrace.yo.user.auth.model.{AuthToken, LoginData}
import org.stacktrace.yo.user.model.AssembleUser

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
    * Retrieves a user that matches the specified ID.
    *
    * @param loginData The login data to retrieve a user.
    * @return The retrieved user or None if no user could be retrieved for the given login data.
    */
  def retrieve(loginData: LoginData): Future[Option[((AssembleUser, AuthToken))]]

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user.
    */
  def save(user: AssembleUser): Future[(AssembleUser, AuthToken)]
}
