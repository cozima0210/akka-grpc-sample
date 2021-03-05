lazy val client = (project in file("client"))
  .settings(
    name := "client",
    scalaVersion := "2.13.5"
  )
  .dependsOn(server)

lazy val server = (project in file("server"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    name := "server",
    scalaVersion := "2.13.5"
  )

lazy val gatling = (project in file("gatling"))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "gatling",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts"  % "3.5.1"  % Test,
      "io.gatling"            % "gatling-test-framework"     % "3.5.1"  % Test,
      "com.github.phisgr"     % "gatling-grpc"               % "0.11.0" % Test,
      "com.google.api.grpc"   % "proto-google-common-protos" % "2.1.0"  % "protobuf-src",
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc"       % scalapb.compiler.Version.scalapbVersion,
      "com.typesafe.akka"    %% "akka-discovery"             % "2.6.11" % Test,
      "com.typesafe.akka"    %% "akka-protobuf"              % "2.6.11" % Test,
      "com.typesafe.akka"    %% "akka-protobuf-v3"           % "2.6.11" % Test,
      "com.typesafe.akka"    %% "akka-stream"                % "2.6.11" % Test
    ),
    PB.targets in Test += scalapb.gen() -> (sourceManaged in Test).value
  )

val root = (project in file("."))
  .settings(
    name := "akka-gprc-sample",
    scalaVersion := "2.13.5"
  )
  .aggregate(
    client,
    server,
    gatling
  )
