package com
package wjss
package iamauthorization

import zhttp.service.Server
import java.io.IOException
import sttp.model.Uri
import zio._
import com.wjss.iamauthorization.repositories.UserRepository

import com.wjss.iamauthorization.api.routes.UserRoutes
import com.wjss.iamauthorization.utils.ServerConfig

object MyApp extends ZIOAppDefault {
  def run = ZIO
    .service[ServerConfig]
    .flatMap { config =>
      Server.start(
        port = config.port,
        http = UserRoutes()
      )
    }
    .provide(
      // A layer responsible for storing the state of the `counterApp`
      // ZLayer.fromZIO(Ref.make(0)),
      // To use the persistence layer, provide the `PersistentUserRepo.layer` layer instead
      UserRepository.layer,
      ServerConfig.layer
    )
}
