package com
package wjss
package iamauthorization
package domain.models

import zio.json.JsonEncoder
import zio.json.DeriveJsonDecoder
import zio.json.JsonDecoder
import zio.json.DeriveJsonEncoder

case class User(name: String, email: String)

object User:
  given JsonEncoder[User] =
    DeriveJsonEncoder.gen[User]
  given JsonDecoder[User] =
    DeriveJsonDecoder.gen[User]
