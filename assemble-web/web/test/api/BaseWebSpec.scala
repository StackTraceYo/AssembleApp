package api

import java.io.File

import akka.actor.ActorSystem
import org.apache.commons.io.FileUtils

import scala.util.Try

/**
  * Created by Stacktraceyo on 9/27/17.
  */
trait BaseWebSpec {

  def system: ActorSystem

  // Obtain information about the event journal and snapshot journal from the Akka configuration
  val storageLocations: List[File] =
    List(
      "akka.persistence.journal.leveldb.dir",
      "akka.persistence.journal.leveldb-shared.store.dir",
      "akka.persistence.snapshot-store.local.dir").map { s =>
      new File(system.settings.config.getString(s))
    }

  def deleteStorageLocations(): Unit = {
    storageLocations.foreach(dir => Try(FileUtils.deleteDirectory(dir)))
  }
}