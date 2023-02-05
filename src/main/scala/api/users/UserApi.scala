package api.users

import zio.*
import zio.json.*
import zhttp.http.*

object UserApi:
  def apply(): Http[UserRepo, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      // POST /users -d '{"name": "John", "age": 35}'
      case req @ (Method.POST -> !! / "users") =>
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
              UserRepo
                .register(user)
                .map(id => Response.text(id))
        yield resp

      // GET /users/:id
      case Method.GET -> !! / "users" / id =>
        UserRepo
          .lookup(id)
          .map {
            case Some(user) =>
              Response.json(user.toJson)
            case None =>
              Response.status(Status.NotFound)
          }
      // GET /users
      case Method.GET -> !! / "users" =>
        UserRepo.users.map(response => Response.json(response.toJson))
    }
