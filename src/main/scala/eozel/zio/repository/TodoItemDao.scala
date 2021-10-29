package eozel.zio.repository
import eozel.zio.domain._
import zio._
import zio.macros._
import zio.stream._

@accessible
trait TodoItemDao {

  def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]]
  def listTodoItems(): Stream[TodoAppError, TodoItem]
  def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit]
}

object TodoItemDao {

  val inMemoryDao: ULayer[Has[TodoItemDao]] = (for {
    todoItemListRef <- Ref.make(Map.empty[Long, TodoItem])

  } yield new TodoItemDao {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = todoItemListRef.get.map(m => m.get(id))

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = for {
      map  <- ZStream.fromEffect(todoItemListRef.get)
      item <- ZStream.fromIterable(map.values)

    } yield item

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      todoItemListRef.getAndUpdate(m => m + (item.id -> item)).unit

  }).toLayer

}
