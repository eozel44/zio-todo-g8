package eozel.zio.repository
import doobie.implicits._
import doobie.util.transactor.Transactor
import eozel.zio.config._
import eozel.zio.domain._
import zio._
import zio.interop.catz._
import zio.stream._

object TodoItemRepositoryDoobie {

  case class TodoItemRepositoryDoobieLive(db: DBConfig) extends TodoItemRepository {

    val xa: Transactor.Aux[Task, Unit] = Transactor.fromDriverManager[Task](
      db.driver,
      db.url,
      db.username,
      db.password
    )

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] =
      sql"""SELECT id, description, owner, finished FROM todos u where u.id = ${id}"""
        .query[TodoItem]
        .option
        .transact(xa)
        .orDie
        .flatMap {
          case Some(item) => ZIO.succeed(Some(item))
          case None       => ZIO.fail(TodoAppDaoError("todo not found"))
        }

    /**
     * *
     *
     * eren: streami nasıl yaparız
     */
    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ???

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      sql"""INSERT INTO todos (id, description, owner, finished) VALUES (${item.id}, ${item.description}, ${item.owner}, ${item.finished}) 
      ON DUPLICATE KEY UPDATE id = ${item.id}, description = ${item.description}, owner = ${item.owner}, finished = ${item.finished}""".update.run
        .transact(xa)
        .orDie
        .unit

  }

  val todoItemRepositoryDoobieLive: ZLayer[Has[DBConfig], Nothing, Has[TodoItemRepository]] =
    TodoItemRepositoryDoobieLive.toLayer
}
