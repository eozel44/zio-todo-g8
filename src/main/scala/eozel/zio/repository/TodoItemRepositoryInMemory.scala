package eozel.zio.repository
import eozel.zio.domain._
import zio._
import zio.stream._

import scala.collection.mutable

object TodoItemRepositoryInMemory {

  val todoItemListRef: mutable.Map[Long, TodoItem] = scala.collection.mutable.Map.empty[Long, TodoItem]

  case class TodoItemRepositoryInMemoryLive() extends TodoItemRepository {

    override def getTodoItem(id: Long): IO[TodoAppError, Option[TodoItem]] = ZIO.succeed(todoItemListRef.get(id))

    override def listTodoItems(): Stream[TodoAppError, TodoItem] = ZStream.fromIterable(todoItemListRef.values)

    override def upsertTodoItem(item: TodoItem): IO[TodoAppError, Unit] =
      ZIO.succeed(todoItemListRef.addOne(item.id -> item))

  }

  val todoItemRepositoryInMemoryLive: ZLayer[Any, Nothing, Has[TodoItemRepository]] =
    TodoItemRepositoryInMemoryLive.toLayer

}
