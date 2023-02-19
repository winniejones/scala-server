import zio._

case class AppState(
    users: Map[User.Id, User]
)

object AppState {
  type State =
    RefM[AppState] & RefM[UserState]

  def layer: URLayer[AppState, State] =
    ZLayer.fromEffectMany(
      for {
        conf     <- ZIO.service[AppState]
        appState <- RefM.make(conf)
        users =
          appState
            .map { state =>
              UserState(
                state.users
              )
            }
            .contramapM { (state: UserState) =>
              appState.get.map(
                _.copy(
                  users = state.users
                )
              )
            }
      } yield appState ++ users
    )
}
