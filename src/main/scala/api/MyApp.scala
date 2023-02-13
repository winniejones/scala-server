package api

import api.greet.GreetingApi
import api.counter.CounterApi
import api.download.DownloadApi
import api.config.*
import api.users.{InmemoryUserRepo, UserApi, PersistentUserRepo}
import zio._
import zhttp.service.Server

import java.io.IOException
import sttp.model.Uri

object MyApp extends ZIOAppDefault {

  def run = ZIO
    .service[ServerConfig]
    .flatMap { config =>
      Server.start(
        port = config.port,
        http = GreetingApi() ++ DownloadApi() ++ CounterApi() ++ UserApi()
      )
    }
    .provide(
      // A layer responsible for storing the state of the `counterApp`
      ZLayer.fromZIO(Ref.make(0)),

      // To use the persistence layer, provide the `PersistentUserRepo.layer` layer instead
      InmemoryUserRepo.layer,
      ServerConfig.layer
    )
}
