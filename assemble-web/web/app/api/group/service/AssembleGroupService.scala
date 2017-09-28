package api.group.service

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import api.group.Request
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.CreateAssembleGroup
import org.stacktrace.yo.group.core.api.{BaseGroupClient, GroupAPIProtocol}
import org.stacktrace.yo.user.auth.model.AssembleUser

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AssembleGroupService @Inject()(as: ActorSystem)(implicit ec: ExecutionContext) {

  lazy val groupClient: BaseGroupClient = new BaseGroupClient(as)


  def createGroup(user: AssembleUser, creationRequest: Request.CreateGroupRequest): Future[GroupAPIProtocol.GroupCreated] = {
    val hostid = user.id
    val groupName = creationRequest.groupName
    val request = CreateAssembleGroup(hostid, groupName)
    groupClient.createGroup(request)
  }

}