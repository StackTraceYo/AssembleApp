import com.trueaccord.scalapb.compiler.Version.scalapbVersion
import play.sbt.PlayImport._
import sbt._


object Dependencies {

  val akkaVersion = "2.5.4"

  val akka =
    Seq(
      // Akka
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
    )

  val json = Seq(
    "com.typesafe.play" % "play-json_2.11" % "2.6.4"
  )

  val playTest = Seq(
    "org.scalatestplus.play" % "scalatestplus-play_2.11" % "3.1.1" % "test"
  )

  val test = Seq(
    "org.mockito" % "mockito-core" % "2.10.0" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  )

  val groupDeps: Seq[ModuleID] =
    test ++
      akka ++
      Seq(
        guice,
        "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
        // Local LevelDB journal (Akka Persistence)
        // http://doc.akka.io/docs/akka/current/scala/persistence.html#Local_LevelDB_journal
        "org.iq80.leveldb" % "leveldb" % "0.9",
        "commons-io" % "commons-io" % "2.4" % "test"
      )

  val groupModelDeps: Seq[ModuleID] =
    test ++
      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"
      )

  val geoDeps: Seq[ModuleID] =
    test ++
      akka ++
      Seq(
        "com.github.davidmoten" % "rtree" % "0.8.0.2",
        "org.apache.commons" % "commons-math3" % "3.0",
        "io.reactivex" %% "rxscala" % "0.26.5"
      )

  val userDependencies: Seq[ModuleID] =
    json ++
      test ++
      playTest ++
      Seq(guice)

  val webDependencies: Seq[ModuleID] =
    json ++
      playTest ++
      test ++
      Seq(
        ehcache,
        guice
      )
}