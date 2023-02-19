package object service {
  type Open[A] = ZIO[Any, AppError, A]
}
