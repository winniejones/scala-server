object UserInMemoryRepository {
  def create(id: User.Id, user: User)(users: Map[User.Id, User]) =
    for {
      _ <- ZIO.fail(AlreadyExists(id)).when(users.contains(id))
    } yield users + (id -> user)

  def update(id: User.Id, user: User)(users: Map[User.Id, User]) =
    for {
      _ <- ZIO.fail(notFound(id)).unless(users.contains(id))
    } yield users + (id -> user)

  def get(id: User.Id)(users: Map[User.Id, User]) =
    for {
      user <-
        ZIO.getOrFailWith(notFound(id)) {
          user.get(id)
        }
    } yield id -> bruker
}
