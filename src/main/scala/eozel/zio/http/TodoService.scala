package eozel.zio.http

import eozel.zio.domain._
import eozel.zio.repository.TodoItemRepository
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import zhttp.http._
import zio._

import scala.util.Try

object TodoService {

  implicit val decodeTodoItem: Decoder[TodoItem] = deriveDecoder[TodoItem]
  implicit val encodeTodoItem: Encoder[TodoItem] = deriveEncoder[TodoItem]

  object LongVar {
    def unapply(i: String): Option[Long] = Try(i.toLong).toOption
  }

  def routes: Http[Has[TodoItemRepository] with Any, TodoAppError, Request, UResponse] =
    Http.collectM[Request] { case Method.GET -> Root / LongVar(id) =>
      for {
        todo <- TodoItemRepository.getTodoItem(id)
      } yield Response.jsonString(todo.asJson.spaces2)

    }

}
