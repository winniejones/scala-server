package com.wjss.iamauthorization.api.users

import zio.Ref

import scala.collection.mutable
import zio.Random
import zio.ZLayer
import zio.UIO

import com.wjss.iamauthorization.domain.models.User
import com.wjss.iamauthorization.domain.services.UserService

case class InmemoryUserRepo(map: Ref[mutable.Map[String, User]])
    extends UserService:
  def create(user: User): UIO[String] =
    for
      id <- Random.nextUUID.map(_.toString)
      _  <- map.updateAndGet(_ addOne (id, user))
    yield id

  def get(id: String): UIO[Option[User]] =
    map.get.map(_.get(id))

  def getAll: UIO[List[User]] =
    map.get.map(_.values.toList)

object InmemoryUserRepo {
  def layer: ZLayer[Any, Nothing, InmemoryUserRepo] =
    ZLayer.fromZIO(
      Ref.make(mutable.Map.empty[String, User]).map(new InmemoryUserRepo(_))
    )
}
