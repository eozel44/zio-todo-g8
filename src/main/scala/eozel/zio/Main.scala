package eozel.zio

import eozel.zio.config._
import eozel.zio.domain._
import eozel.zio.http._
import eozel.zio.repository._
import zhttp.service.Server
import zio._
import zio.logging._
import zio.logging.slf4j._
object Main extends App {

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = {

    val program = for {
      _ <- log.info(s"info message without correlation id")
      _ <- TodoItemRepository.upsertTodoItem(TodoItem(1, "todo", "eren", false))
      _ <- TodoItemRepository.upsertTodoItem(TodoItem(2, "todo", "ahmet", false))
      _ <- TodoItemRepository.upsertTodoItem(TodoItem(3, "todo", "mehmet", false))
      _ <- log.info(s"Items inserted")
      _ <- log.info(s"Service started at 9000 port!")
      _ <- Server.start(9000, TodoService.routes)
    } yield ExitCode.success

    val correlationId: LogAnnotation[String] = LogAnnotation[String](
      name = "correlationId",
      initialValue = "undefined-correlation-id",
      combine = (_, newValue) => newValue,
      render = identity
    )
    val logFormat = "[correlation-id = %s] %s"
    val loggingLayer: ULayer[Logging] =
      Slf4jLogger.make((context, message) => logFormat.format(context(correlationId), message))

    val dbLayerWithLog: ZLayer[Any, TodoAppError, Has[TodoItemRepository]] =
      loggingLayer ++ TodoItemRepositoryInMemory.todoItemRepositoryInMemoryLive

    val dbConfigLayer = AppConfig.configurationLive >>> (for {
      appConfig <- ZIO.service[AppConfig]
    } yield appConfig.db).toLayer

    val dbLayerDoobie: ZLayer[Any, TodoAppError, Has[TodoItemRepository]] =
      dbConfigLayer >>> TodoItemRepositoryDoobie.todoItemRepositoryDoobieLive

    val prodLayer = loggingLayer ++ dbLayerDoobie

    program
      .provideLayer(prodLayer)
      //.catchAll(t => ZIO.succeed(t.printStackTrace()).map(_ => ExitCode.failure))
      .orDie

  }

}
