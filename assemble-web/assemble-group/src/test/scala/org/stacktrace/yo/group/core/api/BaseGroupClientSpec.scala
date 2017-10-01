package org.stacktrace.yo.group.core.api

import akka.actor.ActorSystem
import org.stacktrace.yo.group.AssemblePersistenceSpec
import org.stacktrace.yo.group.core.api.GroupAPIModel.AssembledGroup
import org.stacktrace.yo.group.core.api.GroupAPIProtocol.{CreateAssembleGroup, FindAssembleGroup, GroupRetrieved}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class BaseGroupClientSpec extends AssemblePersistenceSpec(ActorSystem("testSystem")) {

  "An BaseGroupClient" must {

    "forward a create a group request into the system" in {
      val classUnderTest = new BaseGroupClient(system)
      val fResponse = classUnderTest.createGroup(CreateAssembleGroup("group-name", "host-name"))
      val response = Await.result(fResponse, 2 seconds)
      response.groupId should not be empty
    }

    "forward a retrieve group request into the system" in {
      val classUnderTest = new BaseGroupClient(system)
      val fResponse = classUnderTest.createGroup(CreateAssembleGroup("group-name", "host-name"))
      val response = Await.result(fResponse, 2 seconds)
      response.groupId should not be empty

      val fResponse2 = classUnderTest.getGroupInformation(FindAssembleGroup(response.groupId))
      val response2 = Await.result(fResponse2, 5 seconds)

      response2 shouldEqual Some(GroupRetrieved(AssembledGroup(response.groupId)))
      response2.get.groupInformation.groupId shouldEqual response.groupId
    }
  }
}