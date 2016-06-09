lazy val scalaVer = "2.11.7"

lazy val commonSettings = Seq(
  organization := "com.simonpayne",
  version := "0.1.0",
  scalaVersion := scalaVer
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "preinterview test 14052016",    
    libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVer,
    libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
  )


