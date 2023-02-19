import zio.*
import zio.clock.*

class AuthorizationService(
    clock: Clock.Service
) {
  def hasRights: Open[Unit] = ZIO.success(Unit)
}
