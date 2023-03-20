package com.wjss.iamauthorization.repositories

import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{SnakeCase, MysqlZioJdbcContext}
import io.getquill.jdbczio.Quill
import io.getquill.*
import zio.*

import java.util.UUID
import javax.sql.DataSource

import com.wjss.iamauthorization.domain.models.User
import com.wjss.iamauthorization.domain.services.UserService

case class UserTable(
    user_id: UUID,
    name: String,
    email: String
)

case class UserRepository(datasource: DataSource) extends UserService:
  val ctx = new MysqlZioJdbcContext(SnakeCase)

  import ctx._

  override def create(user: User): Task[String] = {
    for
      id <- Random.nextUUID
      _ <- ctx.run {
        quote {
          querySchema[UserTable]("Users").insertValue {
            lift(UserTable(id, user.name, user.email))
          }
        }
      }
    yield id.toString
  }.provideSomeLayer(ZLayer.succeed(datasource))

  override def get(id: String): Task[Option[User]] =
    ctx
      .run {
        quote {
          querySchema[UserTable]("Users")
            .filter(p => p.user_id == lift(UUID.fromString(id)))
            .map(u => User(u.name, u.email))
        }
      }
      .provideSomeLayer(ZLayer.succeed(datasource))
      .map(_.headOption)

  override def getAll: Task[List[User]] =
    ctx
      .run {
        quote {
          querySchema[UserTable]("Users").map(u => User(u.name, u.email))
        }
      }
      .provideSomeLayer(ZLayer.succeed(datasource))

object UserRepository:
  def layer: ZLayer[Any, Throwable, UserRepository] =
    Quill.DataSource.fromPrefix("DatabaseConfig") >>>
      ZLayer.fromFunction(UserRepository(_))
