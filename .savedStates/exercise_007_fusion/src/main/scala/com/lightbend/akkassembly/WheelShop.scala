package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl._

class WheelShop {
  val wheels: Source[Wheel, NotUsed] = {
    Source.repeat(Wheel())
  }

  val installWheels: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = Flow[UnfinishedCar].zip{
    wheels.grouped(4)
  }.map(tuple => tuple._1.installWheels(tuple._2))
}






