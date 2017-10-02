package org.stacktrace.yo.group.core.group.lookup

import akka.actor.{Actor, ActorContext, ActorRef}

import scala.collection.mutable

trait ActorLookup {

  this: Actor =>

  //id to references
  val references: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()
  //ids to actornames
  val actorNames: mutable.HashMap[String, String] = scala.collection.mutable.HashMap[String, String]()

  def resolveActorByNameOrId(context: ActorContext, actorName: String): Option[ActorRef] = {
    context.child(actorName) match {
      case reference@Some(ref) =>
        reference
      case None =>
        lookupReference(actorName) match {
          case reference@Some(ref) =>
            reference
          case None =>
            lookupByName(actorName)
        }
    }
  }

  def storeReference(id: String, ref: ActorRef): Unit = {
    references.put(id, ref)
    actorNames.put(id, ref.path.name)
  }

  private def resolveActorByName(context: ActorContext, actorName: String): Option[ActorRef] = {
    context.child(actorName)
  }

  private def lookupReference(id: String): Option[ActorRef] = {
    references.get(id)
  }

  private def lookupByName(id: String): Option[ActorRef] = {
    actorNames.get(id) match {
      case Some(name) =>
        references.get(name)
      case None =>
        Option.empty
    }
  }
}
