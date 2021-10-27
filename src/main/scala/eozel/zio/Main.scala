package eozel.zio
import zio._
import zio.console._

object Main extends App{

  override def run(args: List[String]): URIO[ZEnv,ExitCode] = putStrLn("hello malatya").exitCode


}