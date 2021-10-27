import sbt._

object Dependencies {
  
  lazy val zioVersion = "1.0.12"
  lazy val zio = "dev.zio" %% "zio" % zioVersion
  lazy val zioStreams = "dev.zio" %% "zio-streams" % zioVersion
  lazy val zioMacros = "dev.zio" %% "zio-macros" % zioVersion
  lazy val zioTest = "dev.zio" %% "zio-test" % zioVersion % Test
  lazy val zioTestSbt = "dev.zio" %% "zio-test-sbt" % zioVersion % Test

  lazy val zioConfigVersion = "1.0.10"
  lazy val zioConfig = "dev.zio" %% "zio-config" % zioConfigVersion

  
}
