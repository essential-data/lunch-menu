package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.model.{Document, Element}
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

/**
  * @author miso
  */
case object Ferdinand extends Restaurant {
  def url: String = "http://www.papanica.sk/sk/denne.php?id=4445&kraj=1"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts("td h3.text") map SimpleDish toSeq
  }
}
