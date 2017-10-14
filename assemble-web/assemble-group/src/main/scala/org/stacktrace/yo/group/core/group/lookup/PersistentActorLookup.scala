package org.stacktrace.yo.group.core.group.lookup

import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef}
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}
import com.stacktrace.yo.assemble.group.GroupProtocol.{DirectorReferenceState, GroupReference, GroupReferenceCreated}
import com.stacktrace.yo.assemble.group.Protocol.Event

import scala.collection.mutable
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}

trait PersistentActorLookup {

  this: PersistentActor with Actor with ActorLogging =>

  implicit val executionContext: ExecutionContext = context.dispatcher
  implicit val timeout: FiniteDuration = 3 seconds

  def rebuild(referenceState: DirectorReferenceState)

  def rebuild(event: Event)

  //id to references
  val references: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()
  val referenceHold: mutable.HashMap[String, ActorRef] = scala.collection.mutable.HashMap[String, ActorRef]()

  var referenceState: DirectorReferenceState = DirectorReferenceState()

  override def receiveRecover: PartialFunction[Any, Unit] = {
    case event: Event =>
      log.debug(s"Recovering Event -> $event")
      updateStateWithRecoverEvent(event)
    case SnapshotOffer(metadata, resetEvent: DirectorReferenceState) =>
      log.debug(s"Recovering Snapshot-> $resetEvent")
      recoverStateWithSnapShot(resetEvent)
    case RecoveryCompleted =>
      log.debug(s"Recovery has completed for $persistenceId with reference size {} :", referenceState.reference.size)
  }

  def storeReference(id: String, ref: ActorRef)(implicit executionContext: ExecutionContext): Future[Unit] = {
    referenceHold.put(id, ref)
    persist(GroupReferenceCreated(id, ref.path.name, ref.path.toSerializationFormat))(storeReferenceState)
    Future.successful[Unit]()
  }

  val storeReferenceState: Event => Unit = {
    case evt@GroupReferenceCreated(id, name, path) =>
      val newRef = id -> GroupReference(name, path)
      referenceHold.remove(id) match {
        case Some(actorRef) =>
          referenceState = referenceState.update(_.reference := referenceState.reference + newRef)
          log.warning(s"Stored $id")
          references.put(id, actorRef)
          saveSnapshot(referenceState)
        case None =>
          resolve(id, name, path)
      }
  }

  def recoverStateWithSnapShot(snapshot: DirectorReferenceState): Unit = {
    rebuild(snapshot)
  }

  def updateStateWithRecoverEvent(event: Event): Unit = {
    rebuild(event)
  }


  private def resolve(id: String, name: String, path: String)(implicit executionContext: ExecutionContext): Unit = {
    //if reference is in temp map
    resolveActorByName(name) match {
      case Some(ref) =>
        Future.successful(references.put(id, ref))
      case None =>
        resolveActorByPath(path).onComplete {
          case Success(ref) =>
            references.put(id, ref)
          case Failure(ex) =>
            log.warning(s"$path does not exist")
            referenceState = referenceState.update(_.reference := referenceState.reference - id)
        }
    }
  }

  def resolveActorByName(actorName: String): Option[ActorRef] = {
    context.child(actorName)
  }

  def resolveActorByPath(path: String): Future[ActorRef] = {
    context.actorSelection(ActorPath.fromString(path)).resolveOne(timeout)
  }

  def resolveActorById(id: String): Option[ActorRef] = {
    references.get(id)
  }
}
