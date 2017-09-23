package api.auth.controller

import javax.inject.{Inject, Singleton}

import akka.actor.{ActorRef, ActorSystem, Cancellable, Props}
import org.stacktrace.yo.user.auth.actor.AuthTokenEvictor
import org.stacktrace.yo.user.auth.actor.Protocol.Clean
import org.stacktrace.yo.user.auth.service.AuthTokenService
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

@Singleton
class AuthController @Inject()(cc: ControllerComponents, authTokenService: AuthTokenService, as: ActorSystem)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val evictor: ActorRef = as.actorOf(AuthController.evictionProp(authTokenService, ec))
  val cleanTick: Cancellable = as.scheduler.schedule(0 millis, 20000 millis, evictor, Clean())


  def index = Action {
    Ok("Auth Service Ready.")
  }
}

object AuthController {

  def evictionProp(authTokenService: AuthTokenService, ec: ExecutionContext): Props = {
    Props(new AuthTokenEvictor(authTokenService)(ec))
  }
}