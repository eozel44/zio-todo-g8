package eozel.zio.repository
import doobie.implicits._
import doobie.util.transactor.Transactor
import eozel.zio.domain._
import zio._
import zio.interop.catz._
import zio.stream._
object TodoItemRepositoryDoobie {

  case class TodoItemRepositoryDoobieLive(xa: Transactor[Task]) extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] =
      sql"""SELECT id, description, owner, finished FROM Todos u where u.id = ${id}"""
        .query[TodoItem]
        .option
        .transact(xa)
        .orDie
        .flatMap {
          case Some(item) => ZIO.succeed(Some(item))
          case None       => ZIO.fail(TodoAppDaoError("todo not found"))
        }

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      sql"""INSERT INTO Todos (id, description, owner, finished) VALUES (${item.id}, ${item.description}, ${item.owner}, ${item.finished})""".update.run
        .transact(xa)
        .orDie
        .unit

  }

  val todoItemRepositoryDoobieLive: URLayer[Has[Transactor[Task]], Has[TodoItemRepositoryDoobieLive]] =
    TodoItemRepositoryDoobieLive.toLayer
}
