ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "Chapter03"
  )

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor-typed" % "2.8.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3")
