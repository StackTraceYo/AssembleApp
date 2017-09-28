package org.stacktrace.yo.group.core.api

import org.stacktrace.yo.group.core.api.AssembleGroupAPIProtocol.CreateAssembleGroup
import org.stacktrace.yo.group.core.api.handler.GroupAPIResponseHandler.GroupCreated

import scala.concurrent.Future

trait APIClient {

  //TODO make create group options for now just pass in the forwarded object
  def createGroup(createGroupOptions: CreateAssembleGroup): Future[GroupCreated]


}
