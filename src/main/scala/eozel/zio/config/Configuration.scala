package eozel.zio.config

import pureconfig.ConfigSource
import pureconfig.generic.auto._
import zio._

case class DBConfig(url: String, driver: String, username: String, password: String)
case class HttpConfig(port: Int)

case class AppConfiguration(
  db: DBConfig,
  http: HttpConfig
)

object Configuration {

  def configurationLive: ZLayer[Any, IllegalStateException, Has[AppConfiguration]] = ZLayer.fromEffect {
    ZIO
      .fromEither(ConfigSource.default.load[AppConfiguration])
      .mapError(failures =>
        new IllegalStateException(
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
