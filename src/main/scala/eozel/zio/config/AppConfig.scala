package eozel.zio.config

import eozel.zio.domain._
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import zio._

// TODO:zio-config env variable ile config oluşturma

case class DBConfig(url: String, driver: String, username: String, password: String)
case class HttpConfig(port: Int)

case class AppConfig(
  db: DBConfig,
  http: HttpConfig
)

object AppConfig {

  def configurationLive: ZLayer[Any, TodoAppError, Has[AppConfig]] = ZLayer.fromEffect {
    ZIO
      .fromEither(ConfigSource.default.load[AppConfig])
      .mapError(failures =>
        TodoAppConfigError(
          s"Error loading configuration: $failures"
        )
      )
  }

  /*
  def loadTest: Task[AppConfiguration] =ZIO
    .fromEither(ConfigSource.default.load[AppConfiguration](ConfigFactory.load("application-test.conf")))
    .mapError(failures =>
      new IllegalStateException(
        s"Error loading configuration: $failures"
      )
    )
   */

}
