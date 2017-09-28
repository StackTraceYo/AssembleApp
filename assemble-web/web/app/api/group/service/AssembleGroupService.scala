package api.group.service

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import org.stacktrace.yo.group.core.api.BaseGroupClient
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, GroupCreated}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AssembleGroupService @Inject()(as: ActorSystem)(implicit ec: ExecutionContext) {

  lazy val groupClient: BaseGroupClient = new BaseGroupClient(as)

  def createGroup(hostId: String, groupName: String): Future[GroupCreated] = {
    groupClient.createGroup(CreateAssembleGroup(hostId, groupName))
  }

}