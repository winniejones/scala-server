import java.time.Instant
final case class User(
  name: User.Name, 
  lastActive: Option[Instant] = None
)

object User {
  object Id extends EntityId {
    def entityKind: String = "user"
  }
  type Id = Id.type

  object Name extends Alias[String]

  given JsonEncoder[User] =
    DeriveJsonEncoder.gen[User]
  given JsonDecoder[User] =
    DeriveJsonDecoder.gen[User]
}