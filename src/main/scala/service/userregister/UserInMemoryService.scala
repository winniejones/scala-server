case class UserState(users: Map[User.Id, User])

class UserInMemoryService(state: RefM[UserState], clock: Clock.Services)
    extends UserService {
  val Users = UserInMemoryRepository

  import Handling.*

  override def updateUser(id: User.Id, user: User) =
    state.modify { state =>
      for {
        s <- 
          Users
            .update(id, user)(s.user)
            .map(userVal => s.copy(user = userVal))
      } yield s
    }
}
