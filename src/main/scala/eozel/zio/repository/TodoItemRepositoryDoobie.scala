package eozel.zio.repository
import zio._
import eozel.zio.domain._
import zio.stream._

object TodoItemRepositoryDoobie {

  case class TodoItemRepositoryDoobieLive() extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = ???

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] = ???

  }

  val todoItemRepositoryDoobieLive: ZLayer[Any, Nothing, Has[TodoItemRepository]] = TodoItemRepositoryDoobieLive.toLayer
}
