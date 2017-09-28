package api.group.service

import akka.actor.ActorSystem
import org.scalatest.{Matchers, WordSpecLike}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.language.postfixOps


class AssembleGroupServiceSpec extends WordSpecLike
  with Matchers {

  val logger: Logger = LoggerFactory.getLogger("Test")
  implicit val as: ActorSystem = ActorSystem("testing")
  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  "The AssembleGroupService" must {

    "create groups" in {
      val classUnderTest = new AssembleGroupService(as)
      val result = Await.result(classUnderTest.createGroup("1234", "test-name"), 5 seconds)
      result.groupId should not be empty
      logger.warn("CREATED: {}", result.groupId)
    }

  }
}