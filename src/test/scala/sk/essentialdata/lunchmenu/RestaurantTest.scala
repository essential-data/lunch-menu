package sk.essentialdata.lunchmenu

import org.scalatest.{AsyncFlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
trait RestaurantTest extends AsyncFlatSpec with Matchers {
  def restaurant: Restaurant

  it should "download" in {
    restaurant.download.map { dishes =>
      println(dishes.mkString("\n"))
      assert(dishes.nonEmpty)
    }
  }
}
