package com.wjss.iamauthorization.api.routes

import zio._
import zio.json.*
import zhttp.http.*

import com.wjss.iamauthorization.domain.models.User
import com.wjss.iamauthorization.domain.services.UserService
import com.wjss.iamauthorization.api.controllers.UserController

object UserRoutes:
  def apply(): Http[UserService, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      // POST /users -d '{"name": "John", "age": 35}'
      case req @ (Method.POST -> !! / "users") =>
        UserController.createUser(req)

      // GET /users/:id
      case Method.GET -> !! / "users" / id =>
        UserController.getUser(id)
      // GET /users
      case Method.GET -> !! / "users" =>
        UserController.listUsers()

      case _ => ZIO.succeed(Response.status(Status.NotFound))
    }
