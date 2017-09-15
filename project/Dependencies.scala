import sbt._
import sbt.Keys._
import play.sbt.PlayImport._

object Dependencies {

  val json = Seq(
    "com.typesafe.play" % "play-json_2.11" % "2.6.4"
  )

  val playTest = Seq(
    "org.scalatestplus.play" % "scalatestplus-play_2.11" % "3.1.1" % "test"
  )

  val webDependencies =
    json ++
    playTest ++
    Seq(
      ehcache,
      guice
    )
}