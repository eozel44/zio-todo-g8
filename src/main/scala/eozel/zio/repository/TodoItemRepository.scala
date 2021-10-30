package eozel.zio.repository

import eozel.zio.domain._
import zio._
import zio.macros.accessible
import zio.stream._

// TODO quill repository, ScalikeJDBC repository
@accessible
trait TodoItemRepository {

  def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]]
  def listTodoItems(): Stream[TodoAppError, TodoItem]
  def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit]

}
