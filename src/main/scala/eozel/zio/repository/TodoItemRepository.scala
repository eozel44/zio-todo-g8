package eozel.zio.repository

import eozel.zio.config._
import eozel.zio.domain._
import zio._
import zio.macros.accessible
import zio.stream._

@accessible
trait TodoItemRepository {

  def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]]
  def listTodoItems(): Stream[TodoAppError, TodoItem]
  def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit]

}
object TodoItemRepository {
  case class TodoItemRepositoryLive(dbconfig: DatabaseConfig) extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = ???

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] = ???

  }

  val todoItemRepositoryLive: URLayer[Has[DatabaseConfig], Has[TodoItemRepositoryLive]] = TodoItemRepositoryLive.toLayer
}
