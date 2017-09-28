package org.stacktrace.yo.group.core.api

import javax.inject.{Inject, Singleton}

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AssembleAPIClient @Inject()(as: ActorSystem)(implicit ec: ExecutionContext) extends APIClient {

  val director: ActorRef = as.actorOf(AssembleAPIClient.directorProp())
  implicit val timeout: Timeout = Timeout(3 seconds)

  override def createGroup(createGroupOptions: AssembleGroupAPIProtocol.CreateAssembleGroup): Future[GroupCreated] = {
    director.ask(createGroupOptions)
      .mapTo[GroupCreated]
  }
}

object AssembleAPIClient {

  def directorProp(): Props = {
    Props[AssembleGroupDirector]
  }
}
