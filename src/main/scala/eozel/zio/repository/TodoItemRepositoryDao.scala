package eozel.zio.repository
import eozel.zio.domain._
import zio._
import zio.stream._

object TodoItemRepositoryDao {

  val inMemoryDao: ZLayer[Any, Nothing, Has[TodoItemRepository]] = (for {
    todoItemListRef <- Ref.make(Map.empty[Long, TodoItem])

  } yield new TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = todoItemListRef.get.map(m => m.get(id))

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = for {
      map  <- ZStream.fromEffect(todoItemListRef.get)
      item <- ZStream.fromIterable(map.values)

    } yield item

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      todoItemListRef.getAndUpdate(m => m + (item.id -> item)).unit

  }).toLayer

}
