package eozel.zio.domain

//error
sealed trait TodoAppError extends Throwable
case class TodoAppDaoError(message: String) extends RuntimeException(message) with TodoAppError

//case class
case class TodoItem(id: Long, description: String, owner: String, finished: Boolean)