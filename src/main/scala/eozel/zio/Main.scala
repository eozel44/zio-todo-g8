package eozel.zio

import eozel.zio.domain._
import eozel.zio.logging._
import eozel.zio.repository._
import zio._
object Main extends App {

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {

    val program: ZIO[Has[Logging] with Has[TodoItemRepository], TodoAppError, Unit] = for {
      _    <- Logging.log("Application Started!")
      _    <- TodoItemRepository.upsertTodoItem(TodoItem(1, "todo", "eren", false))
      _    <- TodoItemRepository.upsertTodoItem(TodoItem(2, "todo", "ahmet", false))
      _    <- TodoItemRepository.upsertTodoItem(TodoItem(3, "todo", "mehmet", false))
      _    <- Logging.log("Items inserted")
      item <- TodoItemRepository.getTodoItem(1)
      _    <- Logging.log(s"Get Item :$item")
    } yield ()

    val dbLayerWithLog: ZLayer[Any, TodoAppError, Has[Logging] with Has[TodoItemRepository]] =
      Logging.loggingLive ++ TodoItemRepositoryInMemory.todoItemRepositoryInMemoryLive

    program
      .provideLayer(dbLayerWithLog)
      .catchAll(t => ZIO.succeed(t.printStackTrace()).map(_ => ExitCode.failure))
      .exitCode

  }

}
