package org.stacktrace.yo.user.auth.store

import org.stacktrace.yo.user.auth.model.{AssembleUser, LoginData}

import scala.concurrent.Future

trait UserStore {


  /**
    * Finds a user by its login info.
    *
    * @param loginData The login info of the user to find.
    * @return The found user or None if no user for the given login info could be found.
    */
  def find(loginData: LoginData): Future[Option[AssembleUser]]

  /**
    * Finds a user by its user ID.
    *
    * @param userID The ID of the user to find.
    * @return The found user or None if no user for the given ID could be found.
    */
  def find(userID: String): Future[Option[AssembleUser]]

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user's id.
    */
  def save(user: AssembleUser): Future[AssembleUser]

}

