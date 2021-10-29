package eozel.zio.config
import eozel.zio.domain._
import zio._
import zio.macros.accessible

@accessible
trait DatabaseConfig {
  def getDBConfig(): UIO[DBConfig]

}

object DatabaseConfig {
  case class DatabaseConfigLive() extends DatabaseConfig {

    override def getDBConfig(): UIO[DBConfig] =
      ZIO.succeed(DBConfig("jdbc:mysql://localhost:3306/ziodb", "org.mariadb.jdbc.Driver", "root", "root"))

  }

  val databaseConfigLive: URLayer[Any, Has[DatabaseConfigLive]] = DatabaseConfigLive.toLayer
}
