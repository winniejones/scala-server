package com.wjss.iamauthorization.domain.services

import com.wjss.iamauthorization.domain.models.User

import zio.{ZIO, Task}
import com.wjss.iamauthorization.repositories.UserRepository

trait UserService:
  def create(user: User): Task[String]

  def get(id: String): Task[Option[User]]

  def getAll: Task[List[User]]

object UserService:
  def create(user: User): ZIO[UserService, Throwable, String] =
    ZIO.serviceWithZIO[UserService](_.create(user))

  def get(id: String): ZIO[UserService, Throwable, Option[User]] =
    ZIO.serviceWithZIO[UserService](_.get(id))

  def getAll: ZIO[UserService, Throwable, List[User]] =
    ZIO.serviceWithZIO[UserService](_.getAll)