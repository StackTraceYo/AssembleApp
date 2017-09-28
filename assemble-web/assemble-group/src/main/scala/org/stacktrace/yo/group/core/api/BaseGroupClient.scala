package org.stacktrace.yo.group.core.api

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, GroupCreated}
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class BaseGroupClient(as: ActorSystem)(implicit ec: ExecutionContext) extends GroupBridge {
  private implicit val timeout: Timeout = Timeout(3 seconds)
  private val director: ActorRef = as.actorOf(BaseGroupClient.directorProp())

  //TODO make create group options for now just pass in the forwarded object

  def createGroup(createGroupOptions: CreateAssembleGroup): Future[GroupCreated] = {
    director.ask(createGroupOptions)
      .mapTo[GroupCreated]
  }
}

trait GroupBridge {


  def createGroup(createGroupOptions: CreateAssembleGroup): Future[GroupCreated]

}

object BaseGroupClient {

  def directorProp(): Props = {
    Props[AssembleGroupDirector]
  }
}
