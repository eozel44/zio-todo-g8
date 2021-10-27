import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.eozel"
ThisBuild / organizationName := "eozel"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / scalafixDependencies += "com.github.vovapolu"  %% "scaluzzi"         % "0.1.20"
ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value) 

lazy val scalacopts = Seq(
  "-feature",
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:postfixOps",
  "-language:higherKinds",
  "-Wunused:imports",
  "-Ymacro-annotations"
) 

lazy val mainProject = (project in file("."))
  .settings(
    name := "zio-todo-g8",
    libraryDependencies ++= Seq(zio,zioConfig,zioMacros,zioStreams,zioTest,zioTestSbt),
    scalacOptions ++= scalacopts
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
