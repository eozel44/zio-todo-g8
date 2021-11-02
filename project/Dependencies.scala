import sbt._

object Dependencies {

  private object Versions {
    val zio         = "1.0.12"
    val pureConfig  = "0.17.0"
    val doobie      = "0.12.1"
    val catsInterop = "2.3.1.0"
    val mariaDB     = "2.6.0"
    val hikari      = "4.0.3"
    val zioLogging  = "0.5.10"
    val log4j       = "2.13.3"
    val disruptor   = "3.4.2"
  }

  object Libraries {

    val zio = Seq(
      "dev.zio" %% "zio"          % Versions.zio,
      "dev.zio" %% "zio-streams"  % Versions.zio,
      "dev.zio" %% "zio-macros"   % Versions.zio,
      "dev.zio" %% "zio-test"     % Versions.zio % Test,
      "dev.zio" %% "zio-test-sbt" % Versions.zio % Test
    )

    val pureConfig = Seq(
      "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig
    )

    val doobie = Seq(
      "org.tpolecat" %% "doobie-core"      % Versions.doobie,
      "dev.zio"      %% "zio-interop-cats" % Versions.catsInterop
    )

    val mariaDB = Seq(
      "org.mariadb.jdbc" % "mariadb-java-client" % Versions.mariaDB
    )

    val hikari = Seq(
      "com.zaxxer" % "HikariCP" % Versions.hikari
    )

    val zioLogging = Seq(
      "dev.zio"   %% "zio-logging"       % Versions.zioLogging,
      "dev.zio"   %% "zio-logging-slf4j" % Versions.zioLogging
    )

    val logging = Seq(
      "org.apache.logging.log4j" % "log4j-core"       % Versions.log4j,
      "org.apache.logging.log4j" % "log4j-slf4j18-impl" % Versions.log4j,
      "com.lmax"                 % "disruptor"        % Versions.disruptor
    )

  }
}
