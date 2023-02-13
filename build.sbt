import Dependencies.*

val scala3Version = "3.2.2"

ThisBuild / scalaVersion     := scala3Version
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "wjss"
ThisBuild / organizationName := "WJ Software Solutions"

lazy val root =
  (project in file("."))
    .settings(
      name := "wjss-scala-server",
      libraryDependencies ++= Seq(
        "dev.zio"                     %% "zio"                 % "2.0.6",
        "dev.zio"                     %% "zio-config"          % "3.0.1",
        "dev.zio"                     %% "zio-config-typesafe" % "3.0.1",
        "dev.zio"                     %% "zio-config-magnolia" % "3.0.1",
        "dev.zio"                     %% "zio-json"            % "0.4.0",
        "io.d11"                      %% "zhttp"               % "2.0.0-RC11",
        "io.getquill"                 %% "quill-zio"           % "4.6.0",
        "io.getquill"                 %% "quill-jdbc-zio"      % "4.6.0",
        "com.h2database"               % "h2"                  % "2.1.214",
        "com.softwaremill.sttp.model" %% "core"                % "1.4.7",
        "org.http4s"                  %% "http4s-core"         % "1.0.0-M38",
        "org.http4s"                  %% "http4s-blaze-server" % "1.0.0-M38",
        "org.scalameta"               %% "munit"               % "0.7.29" % Test
      )
    )

// Uncomment the following for publishing to Sonatype.
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for more detail.

// ThisBuild / description := "Some descripiton about your project."
// ThisBuild / licenses    := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
// ThisBuild / homepage    := Some(url("https://github.com/example/project"))
// ThisBuild / scmInfo := Some(
//   ScmInfo(
//     url("https://github.com/your-account/your-project"),
//     "scm:git@github.com:your-account/your-project.git"
//   )
// )
// ThisBuild / developers := List(
//   Developer(
//     id    = "Your identifier",
//     name  = "Your Name",
//     email = "your@email",
//     url   = url("http://your.url")
//   )
// )
// ThisBuild / pomIncludeRepository := { _ => false }
// ThisBuild / publishTo := {
//   val nexus = "https://oss.sonatype.org/"
//   if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//   else Some("releases" at nexus + "service/local/staging/deploy/maven2")
// }
// ThisBuild / publishMavenStyle := true
