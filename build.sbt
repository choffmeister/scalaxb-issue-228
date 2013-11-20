import ScalaxbKeys._

name := "scalaxb-issue-228"

organization := "de.choffmeister"

version := "0.1.0"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-language:implicitConversions", "-language:postfixOps", "-language:existentials", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.11" % "test",
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)

scalaxbSettings

sourceGenerators in Compile <+= scalaxb in Compile

packageName in scalaxb in Compile := "de.choffmeister.scalaxbissue"

testOptions in Test += Tests.Argument("junitxml", "console")
