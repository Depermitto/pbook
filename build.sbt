val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "pbook",
    version := "0.3",
    assemblyJarName := "pbook.jar",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "info.picocli" % "picocli" % "4.7.6",
      "org.apache.pdfbox" % "pdfbox" % "3.0.2"
    ),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    }
  )
