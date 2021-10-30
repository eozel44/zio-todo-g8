import sbt._

object Dependencies {

  lazy val zioVersion = "1.0.12"
  lazy val zio        = "dev.zio" %% "zio"          % zioVersion
  lazy val zioStreams = "dev.zio" %% "zio-streams"  % zioVersion
  lazy val zioMacros  = "dev.zio" %% "zio-macros"   % zioVersion
  lazy val zioTest    = "dev.zio" %% "zio-test"     % zioVersion % Test
  lazy val zioTestSbt = "dev.zio" %% "zio-test-sbt" % zioVersion % Test

  lazy val zioConfigVersion = "1.0.10"
  lazy val zioConfig        = "dev.zio" %% "zio-config" % zioConfigVersion

  //config
  lazy val pureConfigVersion = "0.17.0"
  lazy val pureconfig        = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion

  //doobie

  lazy val doobieVersion = "0.12.1"
  lazy val doobie        = "org.tpolecat" %% "doobie-core" % doobieVersion
  //lazy val doobieH2       = "org.tpolecat" %% "doobie-h2"       % doobieVersion
  //lazy val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion

  //interop zio
  lazy val catsInteropZio = "dev.zio" %% "zio-interop-cats" % "2.3.1.0"

  //mariadb
  lazy val mariaDB = "org.mariadb.jdbc" % "mariadb-java-client" % "2.6.0"

  lazy val hikariCP = "com.zaxxer" % "HikariCP" % "4.0.3"
}
