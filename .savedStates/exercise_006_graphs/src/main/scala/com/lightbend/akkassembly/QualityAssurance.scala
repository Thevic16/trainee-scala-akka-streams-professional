package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow

object QualityAssurance {
  case class CarFailedInspection(car: UnfinishedCar) extends IllegalStateException("Car Failed Inspection")
}

class QualityAssurance {
  import QualityAssurance._

  val decider: Supervision.Decider = {
    case _: CarFailedInspection => Supervision.Resume
    case _ => Supervision.Stop
  }

  val inspect: Flow[UnfinishedCar, Car, NotUsed] = Flow[UnfinishedCar].collect {
    case UnfinishedCar(Some(color), Some(engine), wheels, upgrade) if wheels.length == 4 =>
      Car(serialNumber = SerialNumber(), color = color, engine = engine, wheels = wheels, upgrade = upgrade)
    case unfinishedCar@UnfinishedCar(_, _, _, _) => throw CarFailedInspection(unfinishedCar)
  }.withAttributes(ActorAttributes.supervisionStrategy(decider))

}
