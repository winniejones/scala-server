package api.users

import zio.json.JsonEncoder
import zio.json.DeriveJsonDecoder
import zio.json.JsonDecoder
import zio.json.DeriveJsonEncoder

case class User(name: String, age: Int)

object User:
  given JsonEncoder[User] =
    DeriveJsonEncoder.gen[User]
  given JsonDecoder[User] =
    DeriveJsonDecoder.gen[User]
