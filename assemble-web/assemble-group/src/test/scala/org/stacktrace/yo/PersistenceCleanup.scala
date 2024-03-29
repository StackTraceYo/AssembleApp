package org.stacktrace.yo

import java.io.File

import akka.actor.ActorSystem
import org.apache.commons.io.FileUtils

import scala.util.Try

/**
  * Created by Stacktraceyo on 9/27/17.
  */
trait PersistenceCleanup {
  def system: ActorSystem

  // Obtain information about the event journal and snapshot journal from the Akka configuration
  val storageLocations: List[File] = {
    val list1 =
      List(
        "akka.persistence.journal.leveldb.dir",
        "akka.persistence.journal.leveldb-shared.store.dir",
        "akka.persistence.snapshot-store.local.dir"
      ).map { s => new File(system.settings.config.getString(s)) }

    val list2 =
      List(
        "target/journal",
        "target/snapshots"
      ).map { s => new File(s) }
    list1 ::: list2
  }


  def deleteStorageLocations(): Unit = {
    storageLocations.foreach(dir => Try(FileUtils.deleteDirectory(dir)))
  }
}