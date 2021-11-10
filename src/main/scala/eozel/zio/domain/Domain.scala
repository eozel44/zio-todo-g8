package eozel.zio.domain

//error
sealed trait TodoAppError extends Throwable
case class TodoAppDaoError(message: String, t: Option[Throwable] = None)
    extends RuntimeException(message, t.getOrElse(null))
    with TodoAppError
case class TodoAppConfigError(message: String) extends IllegalStateException(message) with TodoAppError

//case class
case class TodoItem(id: Long, description: String, owner: String, finished: Boolean)
