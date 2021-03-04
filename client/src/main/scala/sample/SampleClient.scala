package sample

import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import sample.grpc.{SampleRequest, SampleService, SampleServiceClient}

object SampleClient {

  def main(args: Array[String]): Unit = {

    implicit val sys = ActorSystem("SampleClient")
    implicit val ec  = sys.dispatcher

    val clientSettings = GrpcClientSettings.connectToServiceAt("127.0.0.1", 8080).withTls(false)

    val client: SampleService = SampleServiceClient(clientSettings)

    sys.log.info("Performing request")
    client
      .exec(SampleRequest("Bob"))
      .map { r =>
        println(r.message)
      }
      .recover { case e =>
        println(e)
      }
  }
}
