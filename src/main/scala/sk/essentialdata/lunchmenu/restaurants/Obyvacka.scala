package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Obyvacka extends Restaurant {
  def url: String = "https://www.zomato.com/sk/bratislava/ob%C3%BDva%C4%8Dka-cafe-restaurant-star%C3%A9-mesto-bratislava-i/menu#daily"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> element(".tmi-group") >> texts("div.tmi-name") filter {_.contains("/")} map SimpleDish toSeq
  }
}
