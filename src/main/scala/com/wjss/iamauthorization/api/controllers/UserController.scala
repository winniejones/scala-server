package com.wjss.iamauthorization.api.controllers

import zio._

import com.wjss.iamauthorization.domain.models.User
import com.wjss.iamauthorization.domain.services.UserService
import zhttp.http._
import zio.json._

object UserController {

  def createUser(req: Request): ZIO[UserService, Throwable, Response] =
      for
        user <- req.body.asString.map(_.fromJson[User])
        resp <- user match
          case Left(error) =>
            ZIO
              .debug(s"Failed to parse the input: $error")
              .as(
                Response.text(error).setStatus(Status.BadRequest)
              )
          case Right(user) =>
            UserService
              .create(user)
              .map(id => Response.text(id))
      yield resp

  def getUser(id: String): ZIO[UserService, Throwable, Response] =
      UserService
        .get(id)
        .map {
          case Some(user) =>
            Response.json(user.toJson)
          case None =>
            Response.status(Status.NotFound)
        }

  def listUsers(): ZIO[UserService, Throwable, Response] =
      UserService.getAll.map(response => Response.json(response.toJson))
}
