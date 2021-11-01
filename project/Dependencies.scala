import sbt._

object Dependencies {

  private object Versions {
    val zio         = "1.0.12"
    val pureConfig  = "0.17.0"
    val doobie      = "0.12.1"
    val catsInterop = "2.3.1.0"
    val mariaDB     = "2.6.0"
    val hikari      = "4.0.3"
  }

  object Libraries {

    lazy val zio = Seq(
      "dev.zio" %% "zio"          % Versions.zio,
      "dev.zio" %% "zio-streams"  % Versions.zio,
      "dev.zio" %% "zio-macros"   % Versions.zio,
      "dev.zio" %% "zio-test"     % Versions.zio % Test,
      "dev.zio" %% "zio-test-sbt" % Versions.zio % Test
    )

    lazy val pureConfig = Seq(
      "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig
    )

    lazy val doobie = Seq(
      "org.tpolecat" %% "doobie-core"      % Versions.doobie,
      "dev.zio"      %% "zio-interop-cats" % Versions.catsInterop
    )

    lazy val mariaDB = Seq(
      "org.mariadb.jdbc" % "mariadb-java-client" % Versions.mariaDB
    )

    lazy val hikari = Seq(
      "com.zaxxer" % "HikariCP" % Versions.hikari
    )
  }
}
