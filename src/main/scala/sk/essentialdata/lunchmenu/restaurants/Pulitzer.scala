package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Pulitzer extends Restaurant {
  def url: String = "http://www.pulitzeruzlatehojelena.sk/menu/dnesne-obedove-menu"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".pmtitle") map SimpleDish toSeq
  }
}
