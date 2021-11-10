package eozel.zio.repository
import eozel.zio.domain._
import zio._
import zio.stream._

object TodoItemRepositoryInMemory {

  case class TodoItemRepositoryInMemoryLive(itemList: Ref[Map[Long, TodoItem]]) extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = itemList.get.map(k => k.get(id))

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = {
      val result = itemList.get.map(k => k.values.toList)
      val b      = ZStream.fromEffect(result)
      b.flatMap(l => ZStream.fromIterable(l))
    }

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      itemList.getAndUpdate(m => m + (item.id -> item)).unit

  }

  val myZio: ZIO[Any, Nothing, TodoItemRepositoryInMemoryLive] =
    Ref.make(Map.empty[Long, TodoItem]).map(r => TodoItemRepositoryInMemoryLive(r))

  val todoItemRepositoryInMemoryLive: ZLayer[Any, Nothing, Has[TodoItemRepository]] =
    myZio.toLayer

}
