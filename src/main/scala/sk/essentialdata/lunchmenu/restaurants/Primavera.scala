package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Primavera extends Restaurant {
  def url: String = "http://www.pizza-primavera.sk/obedove-menu/"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".denne_menu_container .denne_menu .row .detail .popis") map SimpleDish toSeq
  }
}
