package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object UNas extends Restaurant {
  def url: String = "http://www.unasbistro.sk/dnesne-menu/"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".menu-list .menu-list__items .menu-list__item") take 3 map SimpleDish toSeq
  }
}
