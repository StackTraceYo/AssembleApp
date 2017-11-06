package api.group.service

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import api.group.Request
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, ListUserAssembleGroup}
import org.stacktrace.yo.group.core.api.{BaseGroupClient, GroupAPIProtocol}
import org.stacktrace.yo.user.model.AssembleUser

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AssembleGroupService @Inject()(as: ActorSystem)(implicit ec: ExecutionContext) {

  lazy val groupClient: BaseGroupClient = new BaseGroupClient(as)


  def createGroup(user: AssembleUser, creationRequest: Request.CreateGroupRequest): Future[GroupAPIProtocol.GroupCreated] = {
    val hostid = user.id
    val groupName = creationRequest.groupName
    val category = creationRequest.categoryName
    val request = CreateAssembleGroup(hostid, groupName, category)
    groupClient.createGroup(request)
  }

  def listGroups(user: AssembleUser, listRequest: Request.ListGroupRequest): Future[GroupAPIProtocol.NamedGroupsRetrieved] = {
    groupClient.getGroupListForUser(ListUserAssembleGroup(user.id))
  }

}