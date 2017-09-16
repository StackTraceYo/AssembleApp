package org.stacktrace.yo.user.auth.store.impl

import org.stacktrace.yo.user.auth.model.{AssembleUser, LoginData}
import org.stacktrace.yo.user.auth.store.UserStore

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class AssembleUserStore(implicit ec: ExecutionContext) extends UserStore {

  val users: mutable.HashMap[String, AssembleUser] = mutable.HashMap[String, AssembleUser]()


  /**
    * Finds a user by its login info.
    *
    * @param loginData The login info of the user to find.
    * @return The found user or None if no user for the given login info could be found.
    */
  override def find(loginData: LoginData): Future[Option[AssembleUser]] = {
    //
    throw new UnsupportedOperationException
  }

  /**
    * Finds a user by its user ID.
    *
    * @param userID The ID of the user to find.
    * @return The found user or None if no user for the given ID could be found.
    */
  override def find(userID: String): Future[Option[AssembleUser]] = {
    Future {
      users.get(userID)
    }
  }

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user.
    */
  override def save(user: AssembleUser): Future[AssembleUser] = {
    Future {
      users.put(user.id, user)
      user
    }
  }
}
