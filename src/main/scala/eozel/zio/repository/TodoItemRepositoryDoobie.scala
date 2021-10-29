package eozel.zio.repository
import zio._
import eozel.zio.domain._
import zio.stream._


object TodoItemRepositoryDoobie {
  
  case class TodoItemRepositoryLive() extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = ???

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] = ???

  }

  val todoItemRepositoryLive: ZLayer[Any,Nothing,Has[TodoItemRepository]]= TodoItemRepositoryLive.toLayer
}
