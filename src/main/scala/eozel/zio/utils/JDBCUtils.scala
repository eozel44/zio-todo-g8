package eozel.zio.utils

import com.zaxxer.hikari.HikariDataSource
import eozel.zio.config.DBConfig
import zio._
import zio.stream._

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.time.{Instant, OffsetDateTime, ZoneId}
import javax.sql.DataSource

object JDBCUtils {
  val zoneID: ZoneId = ZoneId.of("Europe/Istanbul")
  def createDatasource(config: DBConfig): UManaged[DataSource] =
    Managed.make(ZIO.succeed {
      val ds = new HikariDataSource()
      ds.setDriverClassName(config.driver)
      ds.setJdbcUrl(config.url)
      ds.setUsername(config.username)
      ds.setPassword(config.password)
      ds.setMinimumIdle(config.minPoolSize)
      ds.setMaximumPoolSize(config.maxPoolSize)
      ds.setIdleTimeout(config.maxIdleTime)
      ds.setValidationTimeout(config.checkoutTimeout)
      ds
    })(ds => ZIO.effect(ds.close()).orDie)

  implicit class RichDataSource(private val ds: DataSource) extends AnyVal {
    def connection: TaskManaged[Connection] =
      Managed.makeExit(ZIO.effect(ds.getConnection).map { c =>
        c.setAutoCommit(false)
        c
      }) { case (c, exitValue) =>
        exitValue match {
          case Exit.Success(_) =>
            ZIO.effect {
              c.commit()
              c.close()
            }.orDie
          case _ =>
            ZIO.effect {
              c.rollback()
              c.close()
            }.orDie
        }
      }
  }

  implicit class RichConnection(private val connection: Connection) extends AnyVal {
    def ps(sql: String): TaskManaged[PreparedStatement] =
      ZManaged.fromAutoCloseable(ZIO.effect(connection.prepareStatement(sql)))
  }

  implicit class RichPreparedStatement[R](private val ps: RManaged[R, PreparedStatement]) extends AnyVal {
    def setString(index: Int, v: String): RManaged[R, PreparedStatement] =
      ps.map { p =>
        p.setString(index, v)
        p
      }

    def setString(index: Int, v: Option[String]): RManaged[R, PreparedStatement] =
      ps.map { p =>
        v.map { i =>
          p.setString(index, i)
          p
        }.getOrElse {
          p.setNull(index, java.sql.Types.VARCHAR)
          p
        }
      }

    def setInt(index: Int, v: Int): RManaged[R, PreparedStatement] =
      ps.map { p =>
        p.setInt(index, v)
        p
      }

    def setBoolean(index: Int, v: Boolean): RManaged[R, PreparedStatement] =
      ps.map { p =>
        p.setBoolean(index, v)
        p
      }

    def setInt(index: Int, v: Option[Int]): RManaged[R, PreparedStatement] =
      ps.map { p =>
        v.map { i =>
          p.setInt(index, i)
          p
        }.getOrElse {
          p.setNull(index, java.sql.Types.INTEGER)
          p
        }
      }
    def setLong(index: Int, v: Long): RManaged[R, PreparedStatement] =
      ps.map { p =>
        p.setLong(index, v)
        p
      }

    def setInstant(index: Int, v: Instant): RManaged[R, PreparedStatement] =
      ps.map { p =>
        p.setObject(index, OffsetDateTime.ofInstant(v, zoneID).toLocalDateTime())
        p
      }

    def setTimestamp(index: Int, v: Option[java.util.Date]): RManaged[R, PreparedStatement] =
      ps.map { p =>
        v.map(_.getTime())
          .map { l =>
            p.setTimestamp(index, new java.sql.Timestamp(l))
            p
          }
          .getOrElse {
            p.setNull(index, java.sql.Types.TIMESTAMP)
            p
          }
      }

    def executeUpdate(): RIO[R, Int] = ps.use(p => ZIO.effect(p.executeUpdate()))

    def executeQuery(): RManaged[R, ResultSet] =
      ps.flatMap(p => ZManaged.fromAutoCloseable(ZIO.effect(p.executeQuery())))

    def execute(): RIO[R, Boolean] =
      ps.use(p => ZIO.effect(p.execute()))
  }
  case class ResultSetIterator[T](r: ResultSet, f: ResultSet => T) extends Iterator[T] {
    def hasNext: Boolean = r.next()

    def next(): T = f(r)
  }

  implicit class RichResultSet(val rs: TaskManaged[ResultSet]) {
    def oneOption[T](f: ResultSet => T): Task[Option[T]] =
      rs.use { r =>
        ZIO.effect(ResultSetIterator(r, f).nextOption())
      }
    def toStream[T](f: ResultSet => T): Stream[Throwable, T] =
      for {
        r <- ZStream.managed(rs)
        t <- ZStream.fromIterator(ResultSetIterator(r, f))
      } yield t
  }
}
