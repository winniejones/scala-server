package api

import api.greet.GreetingApi
import api.counter.CounterApi
import api.download.DownloadApi
import api.users.{InmemoryUserRepo, UserApi, PersistentUserRepo}
import zio.Ref
import zio.Scope
import zio.ZIO
import zio.ZIOAppArgs
import zio.ZIOAppDefault
import zio.ZLayer
import zhttp.service.Server

object MyApp extends ZIOAppDefault:
  def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server
      .start(
        port = 8080,
        http = GreetingApi() ++ CounterApi() ++ UserApi() ++ DownloadApi()
      )
      .provide(
        ZLayer.fromZIO(Ref.make(0)),
        InmemoryUserRepo.layer
      )
