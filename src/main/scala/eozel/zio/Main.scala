package eozel.zio

import eozel.zio.domain._
import eozel.zio.logging._
import eozel.zio.repository._
import zio._
object Main extends App {

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {

    val program: ZIO[Has[Logging] with Has[TodoItemDao], TodoAppError, Unit] = for {
      _    <- Logging.log("Application Started!")
      _    <- TodoItemDao.upsertTodoItem(TodoItem(1, "todo", "eren", false))
      _    <- Logging.log("Item inserted")
      item <- TodoItemDao.getTodoItem(1)
      _    <- Logging.log(s"Inserted Item is:$item")
    } yield ()

    val dbLayerWithLog: ZLayer[Any, TodoAppError, Has[Logging] with Has[TodoItemDao]] =
      Logging.loggingLive ++ TodoItemDao.inMemoryDao

    program
      .provideLayer(dbLayerWithLog)
      .catchAll(t => ZIO.succeed(t.printStackTrace()).map(_ => ExitCode.failure))
      .exitCode

  }

}
