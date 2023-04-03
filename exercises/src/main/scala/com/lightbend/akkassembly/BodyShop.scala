package com.lightbend.akkassembly

import akka.stream.scaladsl.{Flow, Source}

import scala.concurrent.duration.FiniteDuration

class BodyShop(buildTime: FiniteDuration) {
  val cars = Source.repeat(UnfinishedCar()).throttle(1, buildTime)
}
