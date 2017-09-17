package org.stacktrace.yo.user.auth.actor

import akka.actor.{Actor, ActorLogging}
import org.stacktrace.yo.user.auth.actor.Protocol.Clean
import org.stacktrace.yo.user.auth.service.AuthTokenService

import scala.concurrent.ExecutionContext


/**
  * This Actor is used to call the AuthTokenService and evict tokens that are expired.
  */

class AuthTokenEvictor(authTokenService: AuthTokenService)(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  override def receive: Receive = {
    case msg@Clean() =>
      authTokenService.clean
        .map(tokensCleaned => {
          log.info("Evicted {} tokens", tokensCleaned.size)
          tokensCleaned.foreach(token => {
            log.debug("Evicted USER: {} - TOKEN {}", token.id)
          })
        })
  }
}
