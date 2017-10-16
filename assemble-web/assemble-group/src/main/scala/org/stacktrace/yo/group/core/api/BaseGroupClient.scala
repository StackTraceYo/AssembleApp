package org.stacktrace.yo.group.core.api

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.stacktrace.yo.assemble.group.Protocol.CreateGroupAccess
import org.stacktrace.yo.access.core.access.GroupAccessActor
import org.stacktrace.yo.group.core.api.GroupAPIProtocol._
import org.stacktrace.yo.group.core.group.director.AssembleGroupDirector

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class BaseGroupClient(as: ActorSystem)(implicit ec: ExecutionContext) extends GroupBridge with AccessBridge {
  private implicit val timeout: Timeout = Timeout(3 seconds)
  private val director: ActorRef = as.actorOf(BaseGroupClient.directorProp())
  private val access: ActorRef = as.actorOf(BaseGroupClient.accessProp(director))

  //TODO make create option classes for each for now just pass in the forwarded object/request

  def createGroup(createGroupOptions: CreateAssembleGroup): Future[GroupCreated] = {
    director.ask(createGroupOptions)
      .mapTo[GroupCreated]
      .map(group => {
        access ! CreateGroupAccess(createGroupOptions.hostId, group.groupId)
        group
      })
  }

  def getGroup(findGroupOptions: FindAssembleGroup): Future[Option[GroupRetrieved]] = {
    director.ask(findGroupOptions)
      .mapTo[Option[GroupRetrieved]]
  }

  def getGroupList(findGroupOptions: ListAssembleGroup): Future[GroupsRetrieved] = {
    director.ask(findGroupOptions)
      .mapTo[GroupsRetrieved]
  }
}

trait GroupBridge {


  def createGroup(createGroupOptions: CreateAssembleGroup): Future[GroupCreated]

  def getGroup(findGroupOptions: FindAssembleGroup): Future[Option[GroupRetrieved]]

  def getGroupList(findGroupOptions: ListAssembleGroup): Future[GroupsRetrieved]

}

trait AccessBridge {

}

object BaseGroupClient {

  def directorProp()(implicit executionContext: ExecutionContext): Props = {
    Props(new AssembleGroupDirector())
  }

  def accessProp(director: ActorRef)(implicit executionContext: ExecutionContext): Props = {
    Props(new GroupAccessActor(director))
  }
}
