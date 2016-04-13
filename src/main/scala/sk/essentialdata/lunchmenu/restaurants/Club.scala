package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Club extends Restaurant {
  def url: String = "https://www.zomato.com/sk/bratislava/club-restaurant-star%C3%A9-mesto-bratislava-i"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".tmi-name") drop 2 map SimpleDish toSeq
  }
}
