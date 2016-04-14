package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Ferdinand extends Restaurant {
  def url: String = "http://www.papanica.sk/sk/denne.php?id=4445&kraj=1"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> element("#right table") >> texts("td h3.text") map SimpleDish toSeq
  }
}
