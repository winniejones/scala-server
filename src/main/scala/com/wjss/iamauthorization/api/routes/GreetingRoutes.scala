package com
package wjss
package iamauthorization
package api.routes

import zhttp.http.*

object GreetingRoutes:
  def apply(): Http[Any, Nothing, Request, Response] =
    Http.collect[Request] {
      // GET /greet?name=:name
      case req @ (Method.GET -> !! / "greet")
          if (req.url.queryParams.nonEmpty) =>
        Response.text(
          s"Hello ${req.url.queryParams("name").mkString(" and ")}!"
        )

      // GET /greet
      case Method.GET -> !! / "greet" =>
        Response.text(s"Hello World!")

      // GET /greet
      case Method.GET -> !! =>
        Response.text(s"Hello World!")

      // GET /greet/:name
      case Method.GET -> !! / "greet" / name =>
        Response.text(s"Hello $name!")
    }
