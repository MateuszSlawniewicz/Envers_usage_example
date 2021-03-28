lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """play-java-jpa-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      javaWs,
      ehcache,
      guice,
      javaJdbc,
      javaJpa,
      "org.mindrot" % "jbcrypt" % "0.4",
      "org.postgresql" % "postgresql" % "42.2.8",
      "org.hibernate" % "hibernate-core" % "5.4.9.Final",
      "com.google.code.gson" % "gson" % "2.8.6",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.10.2",
      javaWs % "test",
      "org.awaitility" % "awaitility" % "4.0.1" % "test",
      "org.assertj" % "assertj-core" % "3.14.0" % "test",
      "org.mockito" % "mockito-core" % "3.1.0" % "test",
      "org.hibernate" % "hibernate-envers" % "5.4.30.Final",
    ),
    Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v"),
    scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked"),
    javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation", "-Werror"),
    PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
  )
