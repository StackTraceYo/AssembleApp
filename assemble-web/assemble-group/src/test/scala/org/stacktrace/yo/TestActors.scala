package org.stacktrace.yo

import akka.actor.{Actor, ActorLogging}
import org.stacktrace.yo.TestActors.ExceptionThrowingActor.Throw

/**
  * Created by Stacktraceyo on 9/29/17.
  */
object TestActors {

  class ExceptionThrowingActor extends Actor with ActorLogging {

    override def receive: Receive = {
      case Throw(e) =>
        log.error("Throwing {}", e.getClass.getName)
        throw e
    }
  }

  object ExceptionThrowingActor {

    case class Throw(exception: Throwable)

  }


}
