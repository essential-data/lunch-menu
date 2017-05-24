package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Mediterraneo extends Restaurant {
  def url: String = "https://www.zomato.com/sk/bratislava/restaurante-mediterraneo-star%C3%A9-mesto-bratislava-i/denn%C3%A9-menu"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".tmi-daily") map SimpleDish toSeq
  }
}
