import java.util.UUID

trait AppError
trait DomainError extends AppError

object DomainError {
  case class AlreadyExists(id: UUID)              extends DomainError
  case class NotFoundError(resources: List[UUID]) extends DomainError
  case class ValidationError(fail: List[String])  extends DomainError

  def notFound(id: UUID): AppError = NotFoundError(id :: Nil)
}
