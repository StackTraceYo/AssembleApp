package org.stacktrace.yo.group.core.group.supervisor

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop}
import akka.actor.{Actor, OneForOneStrategy}

import scala.concurrent.duration._
import scala.language.postfixOps

trait GroupSupervisionStrategy {

  this: Actor =>


  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception => Escalate
    }
}
