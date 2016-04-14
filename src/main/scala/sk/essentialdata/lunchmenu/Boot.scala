package sk.essentialdata.lunchmenu

import sk.essentialdata.lunchmenu.restaurants._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
object Boot extends scala.App with SolrManager {

  while(true) {
    Seq(Bmp, Budvar, Club, Ferdinand, Flagship, Lanai, Mamut, Millenium, Napoli, Obyvacka, Pulitzer, Staromestsky, Street).map(process)
    Thread.sleep(100000)
  }

  def process(restaurant: Restaurant) = {
    restaurant.download map { case dishes =>
      println(Console.YELLOW + s" ${restaurant.name} " + Console.RESET)
      indexDishes(dishes, restaurant)
    }
  }
}
