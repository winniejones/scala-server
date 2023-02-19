



object env {
/*<script>*/val configuration =
  Map(
    "HOST" -> "containers-us-west-172.railway.app",
    "JDBC_PARAMETERS" -> "jdbc:postgresql://postgres:wFsrQAhi7Ls58W5ONKAn@containers-us-west-172.railway.app:7767/railway",
    "GATEKEEPER_USERNAME" -> "wj",
    "GATEKEEPER_PASSWORD" -> "123",
    "GATEKEEPER_NAME"     -> "local"
  )
/*</script>*/ /*<generated>*/
def args = env_sc.args$
  /*</generated>*/
}

object env_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }
  def main(args: Array[String]): Unit = {
    args$set(args)
    env.hashCode() // hasCode to clear scalac warning about pure expression in statement position
  }
}

