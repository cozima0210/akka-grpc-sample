import com.github.phisgr.gatling.grpc.Predef._
import com.github.phisgr.gatling.grpc.protocol.GrpcProtocol
import io.gatling.core.Predef._
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}
import io.grpc.{ManagedChannelBuilder, Status}
import sample.grpc.sample.{SampleRequest, SampleServiceGrpc}

import scala.concurrent.duration._

class SampleScenarioSimulation extends Simulation {

  val managedChannelBuilder: ManagedChannelBuilder[_] =
    ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext()

  val grpcConf: GrpcProtocol = grpc(channelBuilder = managedChannelBuilder)

  val gatlingScenario: ScenarioBuilder = scenario("Scenario of throughout the session")
    .exec(
      grpc("sample")
        .rpc(SampleServiceGrpc.METHOD_EXEC)
        .payload(SampleRequest("Bob"))
        .check(statusCode is Status.Code.OK)
    )

  def population: PopulationBuilder = gatlingScenario
    .inject(constantUsersPerSec(30) during 1.minutes)
    .protocols(grpcConf)

  setUp(population)
}
