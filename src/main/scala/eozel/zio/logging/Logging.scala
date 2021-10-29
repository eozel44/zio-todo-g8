package eozel.zio.logging
import zio._
import zio.macros.accessible

@accessible
trait Logging {
  def log(line: String): UIO[Unit]
}

object Logging {
  case class LoggingLive() extends Logging {

    override def log(line: String): UIO[Unit] = ZIO.succeed(println(line))

  }

  val loggingLive: ULayer[Has[Logging]] = LoggingLive.toLayer
}
