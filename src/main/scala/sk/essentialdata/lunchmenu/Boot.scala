package sk.essentialdata.lunchmenu

import sk.essentialdata.lunchmenu.restaurants._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
object Boot extends scala.App {
  while(true) {
    Seq(Bmp, Budvar, Club, Ferdinand, Flagship, Lanai, Mamut, Millenium, Napoli, Obyvacka, Staromestsky, Street).map(process)
    Thread.sleep(100000)
  }

  def process(restaurant: Restaurant) = {
    restaurant.download map {case (name, dishes) =>
      println(Console.YELLOW + s" $name " + Console.RESET)
      dishes.foreach(dish =>
        println(s" ${dish.name}")
      )
    }
  }
}
