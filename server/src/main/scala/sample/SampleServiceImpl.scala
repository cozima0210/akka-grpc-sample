package sample

import scala.concurrent.Future
import akka.stream.Materializer
import sample.grpc.{SampleRequest, SampleResponse, SampleService}

class SampleServiceImpl(implicit mat: Materializer) extends SampleService {

  override def exec(in: SampleRequest): Future[SampleResponse] = {
    println(s"${in.name}")
    Future.successful(SampleResponse(s"Executed, ${in.name}"))
  }
}
