package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Flow

class QualityAssurance {
  val inspect: Flow[UnfinishedCar, Car, NotUsed] = Flow[UnfinishedCar].collect {
    case UnfinishedCar(Some(color), Some(engine), wheels, upgrade) if wheels.length == 4 =>
      Car(serialNumber = SerialNumber(), color = color, engine = engine, wheels = wheels, upgrade = upgrade)
  }
}
