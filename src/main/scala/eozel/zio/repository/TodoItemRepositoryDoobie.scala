package eozel.zio.repository
import zio._
import eozel.zio.domain._
import eozel.zio.config._
import zio.stream._


object TodoItemRepositoryDoobie {
  
  case class TodoItemRepositoryLive(dbconfig: DatabaseConfig) extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = ???

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] = ???

  }

  val todoItemRepositoryLive: URLayer[Has[DatabaseConfig], Has[TodoItemRepositoryLive]] = TodoItemRepositoryLive.toLayer
}
