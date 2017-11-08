enablePlugins(ScalaJSPlugin)

name := "OTable"
scalaVersion := "2.12.3" // or any other Scala version >= 2.10.2

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

// https://mvnrepository.com/artifact/org.scala-js/scalajs-dom_sjs0.6_2.12
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.3"

