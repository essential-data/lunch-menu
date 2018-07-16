package sk.essentialdata.lunchmenu

import org.scalatest.{AsyncFlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
trait RestaurantTest extends AsyncFlatSpec with Matchers {
  def restaurant: Restaurant

  it should "download" in {
    println(s"Start ${restaurant.name}")
    restaurant.download.map { dishes =>
      println(s"Stop ${restaurant.name}")
      println(dishes.mkString("\n"))
      println(dishes.map(_.sanitizedName).mkString("\n"))
      assert(dishes.nonEmpty)
    }
  }
}
