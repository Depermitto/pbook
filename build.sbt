val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "pbook",
    version := "0.1",
    assemblyJarName := "pbook.jar",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )
