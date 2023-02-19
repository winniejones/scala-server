trait EndpointCollection[R] { self =>
  def asList: List[Endpoint]

  implicit class NotImplementesOps[R0](
      val endpoint: PartialEndpoint[R0]
  ) {
    def notImplemented =
      endpoint.serverLogic[R](_ => ZIO.fail(Error.NotImplemented()))
  }

  implicit class NotImplementedDescOps[R, I, O](
      val endpoint: ZServerEndpoint[R, I, Error, O]
  ) {
    def markNotImplemented =
      endpoint
        .description(s"*Not implemented.*\n\n$description")
        .tag("Not implemented")

    def description = endpoint.info.description.getOrElse("")
  }

  type Endpoint = ZServerEndpoint[R, _, Error, _]
}

object EndpointCollection {
  def genericEndpoint[R](
      mapping: Error.Mapping,
      baseEndpoint: EndpointCollection.AuthorizedEndpoint[R],
      moduleNames: List[String]
  ) = {
    val endpoint =
      moduleNames.foldLeft(baseEndpoint) { case (endpoint, segment) =>
        endpoint.andThen(_.in(segment.toLowerCase))
      }

    val tagFromModuleNames = moduleNames.reduce((acc, next) => s"$acc / $next")

    endpoint(mapping).tag(tagFromModuleNames)
  }
}
