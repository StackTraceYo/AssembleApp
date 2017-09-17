import sbt.Level
import sbtassembly.Log4j2MergeStrategy

name := AssembleBuild.NamePrefix + "root"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


lazy val client = project.
  settings(Common.settings: _*)

lazy val `user-api` = project.
  settings(Common.settings: _*).
  settings(
    libraryDependencies ++= Dependencies.userDependencies
  )

lazy val `assemble-group` = project.
  settings(Common.settings: _*).
  settings(
    libraryDependencies ++= Dependencies.groupDeps
  )

lazy val web = project.
  dependsOn(`user-api`,`assemble-group`).
  enablePlugins(PlayScala).
  settings(Common.settings: _*).
  settings(
    libraryDependencies ++= Dependencies.webDependencies,
    assemblyMergeStrategy in assembly := {
      case n if n.startsWith("reference.conf") => MergeStrategy.concat
      case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.last
      case PathList(ps@_*) if ps.last == "Log4j2Plugins.dat" => Log4j2MergeStrategy.plugincache
      case PathList("org", "slf4j", xs@_*) => MergeStrategy.first
      case PathList("org", "apache", "commons", "logging", xs@_*) =>
        MergeStrategy.first
      case ps =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(ps)
    },
    sbt.Keys.test in assembly := {}
  )


//lazy val chain = project.
//  dependsOn(domain, common).
//  settings(Common.settings: _*).
//  settings(libraryDependencies ++= Dependencies.chainDependencies)

lazy val root = (project in file(".")).
  aggregate(web, `user-api`)
  .settings(
    sbt.Keys.test in assembly := {}
  )