lazy val root = (project in file("."))
  .enablePlugins(PlayScala, JavaAgent)
  .settings(
    name := """play-scala-with-jmx""",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    javaAgents += JavaAgent( "org.jolokia" % "jolokia-agent-jvm" % "2.0.0-M3" classifier "agent", arguments = "host=0.0.0.0,port=8778"),

    resolvers += Resolver.bintrayRepo("tersesystems", "maven"),
    libraryDependencies ++= Seq(
      guice,
      "com.tersesystems.logback" % "logback-structured-config" % "0.13.1",
      "com.tersesystems.jmxbuilder" % "jmxbuilder" % "0.0.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    ),

    // Point the Play logs at the right place.
    Docker / defaultLinuxLogsLocation := (Docker / defaultLinuxInstallLocation).value + "/logs",
    dockerExposedVolumes := Seq((Docker / defaultLinuxLogsLocation).value),
    dockerEnvVars := Map(
      "JAVA_TOOL_OPTIONS" -> "-Dpidfile.path=/dev/null",
      "LOG_DIR" -> (Docker / defaultLinuxLogsLocation).value
    ),
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(9000, 8778), // expose the jolokia port
)
