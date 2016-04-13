package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SimpleDish}

/**
  * @author miso
  */
case object Bmp extends Restaurant {
  def url: String = "http://dunajska.mestianskypivovar.sk/pivovar-dunajska-ponuka-dna-denne-menu-bratislava-dobry-obed"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts(".eat-list tr") map SimpleDish toSeq
  }
}
