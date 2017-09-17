package org.stacktrace.yo.user.auth.service.impl

import javax.inject.{Inject, Singleton}

import org.stacktrace.yo.user.auth.model.{AssembleUser, LoginData}
import org.stacktrace.yo.user.auth.service.UserService
import org.stacktrace.yo.user.auth.store.UserStore

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AssembleUserService @Inject()(userStore: UserStore)(implicit ec: ExecutionContext) extends UserService {
  /**
    * Retrieves a user that matches the specified ID.
    *
    * @param id The ID to retrieve a user.
    * @return The retrieved user or None if no user could be retrieved for the given ID.
    */
  override def retrieve(id: String): Future[Option[AssembleUser]] = {
    userStore.find(id)
  }


  /**
    * Retrieves a user that matches the specified ID.
    *
    * @param loginData The login data to retrieve a user.
    * @return The retrieved user or None if no user could be retrieved for the given login data.
    */
  override def retrieve(loginData: LoginData): Future[Option[AssembleUser]] = {
    userStore.find(loginData)
  }

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user.
    */
  override def save(user: AssembleUser): Future[AssembleUser] = {
    userStore.save(user)
  }
}
