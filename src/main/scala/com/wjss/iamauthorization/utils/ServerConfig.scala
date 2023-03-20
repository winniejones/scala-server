package com
package wjss
package iamauthorization
package utils

import sttp.model.Uri
import zio._
import zio.config._, ConfigDescriptor._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.config.magnolia.Descriptor

case class ServerConfig(host: String, port: Int, allowedOrigins: List[String])

object ServerConfig {
  val layer: ZLayer[Any, ReadError[String], ServerConfig] =
    ZLayer {
      read {
        descriptor[ServerConfig].from(
          TypesafeConfigSource.fromResourcePath.at(
            PropertyTreePath.$("ServerConfig")
          )
        )
      }
    }
}
