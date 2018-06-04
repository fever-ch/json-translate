val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

val googleCloudTranslate = "com.google.cloud" % "google-cloud-translate" % "1.32.0"

val json4sNative = "org.json4s" %% "json4s-native" % "3.5.4"
val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.5.4"

val scopt = "com.github.scopt" %% "scopt" % "3.7.0"

lazy val commonSettings = Seq(
  organization := "ch.fever",
  scalaVersion := "2.12.6",
  version := "0.1-beta",
  scalacOptions in(Compile, doc) := Seq("-unchecked", "-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    scalaTest % "test"),
  exportJars := true,
  crossPaths := false
)

lazy val fvrSettings = commonSettings ++ Seq(version := "0.1.0-SNAPSHOT")

lazy val `jsontranslate-specifications` = (project in file("specifications")).

  settings(fvrSettings, libraryDependencies ++= Seq(
    json4sNative % Test,
    json4sJackson % Test))

lazy val `jsontranslate-googletranslate` = (project in file("google-translate")).
  settings(fvrSettings, libraryDependencies += googleCloudTranslate)
  .dependsOn(`jsontranslate-specifications` % "test->test;compile->compile")

lazy val `jsontranslate-core` = (project in file("core")).
  settings(fvrSettings, libraryDependencies ++= Seq(
    json4sNative,
    json4sJackson))
  .dependsOn(`jsontranslate-specifications` % "test->test;compile->compile")

lazy val jsontranslate = (project in file("app")).
  settings(fvrSettings,
    logLevel := Level.Error,
    logLevel in assembly := Level.Error,
    libraryDependencies += scopt,
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs@_*) =>
        xs map {
          _.toLowerCase
        } match {
          case "manifest.mf" :: Nil | "index.list" :: Nil | "dependencies" :: Nil => MergeStrategy.discard
          case _ => MergeStrategy.discard
        }

      case _ => MergeStrategy.first
    }
  ).enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "ch.fever.jsontranslate"
  ).dependsOn(`jsontranslate-specifications`, `jsontranslate-core`, `jsontranslate-googletranslate`)

lazy val root = (project in file(".")).
  settings(fvrSettings).
  settings(name := "json-translate").
  aggregate(`jsontranslate-core`, `jsontranslate-googletranslate`, jsontranslate)

