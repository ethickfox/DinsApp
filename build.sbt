name := "DinsApp"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-testkit" %  "2.5.23",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8",
  "org.specs2" %% "specs2-core" % "4.5.1",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.h2database" % "h2" % "1.4.200",

  "org.slf4j" % "slf4j-simple" % "2.0.0-alpha1" % Test,
  "org.slf4j" % "slf4j-api" % "2.0.0-alpha1",
  "org.slf4j" % "slf4j-log4j12" % "2.0.0-alpha1" % Test,
  "org.slf4j" % "slf4j-nop" % "2.0.0-alpha1",

  "org.postgresql" % "postgresql" % "42.2.14",

  "com.google.code.gson" % "gson" % "2.8.6"

)