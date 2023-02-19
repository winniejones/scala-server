import sttp.model.StatusCode
import sttp.tapir.*

sealed trait Error {
  def message: String
}

object Error {
  def defaultHandling[E](error: E): URIO[console.Console, Error] =
    error match {
      case e: Error => ZIO.succeed(e)
      case _ =>
        console
          .putStrLnErr(error.toString)
          .igonre
          .as(
            Error.InternalServerError()
          )
    }

  case class NotFound(
      message: String = NotFound.message,
      resources: List[UUID]
  ) extends Error
  object NotFound {
    val message = "One or more resources queried does not exist."
    val mapping =
      statusMapping(
        StatusCode.NotFound,
        jsonBody[NotFound]
          .description(message)
          .example(
            NotFound(ressurser = UUID.randomUUID :: Nil)
          )
      )
    implicit def codec: JsonCodec[NotFound] =
      JsonCodec.from(
        deriveDecoder,
        prune(deriveEncoder)
      )
  }

  case class InternalServerError(
      message: String = InternalServerError.message
  ) extends Error

  object InternalServerError {
    val message = "Something went wrong."
    val mapping =
      statusMapping(
        StatusCode.InternalServerError,
        jsonBody[InternalServerError]
          .description(message)
          .example(
            InternalServerError()
          )
      )

    implicit def codec: JsonCodec[InternalServerError] =
      JsonCodec.from(
        deriveDecoder,
        prune(deriveEncoder)
      )
  }

  case class NotImplemented(
      message: String = NotImplemented.message
  ) extends Error
  object NotImplemented {
    val message = "Endpoint is not implemented."
    val mapping =
      statusMapping(
        StatusCode.NotImplemented,
        jsonBody[NotImplemented]
          .description(message)
          .example(
            NotImplemented()
          )
      )
    implicit def codec: JsonCodec[NotImplemented] =
      JsonCodec.from(
        deriveDecoder,
        prune(deriveEncoder)
      )
  }

  type Mapping = EndpointOutput.OneOf[Error, Error]

  lazy val read: Error.Mapping =
    oneOf[Error](
      NotImplemented.mapping,
      InternalServerError.mapping,
      NotFound.mapping
    )

  lazy val basic: Error.Mapping =
    oneOf[Error](
      Error.NotImplemented.mapping,
      Error.NotFound.mapping,
      Error.InternalServerError.mapping
    )
}
