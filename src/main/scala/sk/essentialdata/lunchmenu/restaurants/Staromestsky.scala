package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Staromestsky extends Restaurant {
  def url: String = "https://www.zomato.com/sk/bratislava/staromestsk%C3%BD-pub-restaurant-star%C3%A9-mesto-bratislava-i/menu#daily"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".tmi-name") dropWhile {_.startsWith("!!!")} map SimpleDish toSeq
  }
}
