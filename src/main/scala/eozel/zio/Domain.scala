package eozel.zio

//error
sealed trait TodoAppError extends Throwable

//case class
case class TodoItem(id: Long, description: String, owner: String, finished: Boolean)
case class TodoAppDaoError(message: String) extends RuntimeException(message) with TodoAppError
