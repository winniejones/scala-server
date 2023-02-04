import zio._
import zio.Console._

object MyApp extends ZIOAppDefault {
  def run = myAppLogic

  val myAppLogic = 
    for {
      _ <- printLine("Hello! What's your name?") 
      name <- readLine 
      _ <- printLine(s"Hello,{name}, welcome to ZIO!") 
    } yield ()
}
