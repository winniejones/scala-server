package api.config

import sttp.model.Uri
import pureconfig._
import pureconfig.generic.derivation.default._

sealed trait AppConfig derives ConfigReader

final case class ServerConfig (host: String, port: Int)

final case class Server (
    host: String,
    port: Int,
    allowedOrigins: List[Int]
) extends AppConfig
final case class Database (
    className: String,
    url: String,
    user: String,
    pass: String
) extends AppConfig
