package org.stacktrace.yo.user.auth

import com.google.inject.AbstractModule
import org.stacktrace.yo.user.auth.service.impl.{AssembleAuthTokenService, AssembleUserService}
import org.stacktrace.yo.user.auth.service.{AuthTokenService, UserService}
import org.stacktrace.yo.user.auth.store.impl.{AssembleAuthTokenStore, AssembleUserStore}
import org.stacktrace.yo.user.auth.store.{AuthTokenStore, UserStore}

class AuthModule extends AbstractModule {
  override def configure(): Unit = {

    bind(classOf[AuthTokenStore])
      .to(classOf[AssembleAuthTokenStore])
    bind(classOf[UserStore])
      .to(classOf[AssembleUserStore])
    bind(classOf[AuthTokenService])
      .to(classOf[AssembleAuthTokenService])
    bind(classOf[UserService])
      .to(classOf[AssembleUserService])

  }
}
