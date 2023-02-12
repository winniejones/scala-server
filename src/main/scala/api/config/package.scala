package api

import java.nio.file.Paths
import zio.System
import zio.Console
import pureconfig._
import pureconfig.generic.derivation.default._

package object config {
  val appConfig = loadConfigOrThrow[AppConfig]
}
