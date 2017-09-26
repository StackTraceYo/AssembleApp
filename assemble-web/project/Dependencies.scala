import play.sbt.PlayImport._
import sbt._
import com.trueaccord.scalapb.compiler.Version.scalapbVersion


object Dependencies {


  val json = Seq(
    "com.typesafe.play" % "play-json_2.11" % "2.6.4"
  )

  val playTest = Seq(
    "org.scalatestplus.play" % "scalatestplus-play_2.11" % "3.1.1" % "test"
  )

  val test = Seq(
    "org.mockito" % "mockito-core" % "2.10.0" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"
  )

  val groupDeps: Seq[ModuleID] =
    test ++
      Seq(
        "com.typesafe.akka" % "akka-actor_2.11" % "2.5.4",
        "com.typesafe.akka" % "akka-testkit_2.11" % "2.5.4"
      )

  val groupModelDeps: Seq[ModuleID] =
    test ++
      Seq(
        "com.trueaccord.scalapb"      %% "scalapb-runtime"  % scalapbVersion  % "protobuf"
      )

  val geoDeps: Seq[ModuleID] =
    test ++
      Seq(
        "com.typesafe.akka" % "akka-actor_2.11" % "2.5.4",
        "com.typesafe.akka" % "akka-testkit_2.11" % "2.5.4"
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