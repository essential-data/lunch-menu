package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

import scala.language.postfixOps

/**
  * @author miso
  */
case object Kozlovna extends Restaurant {
  def url: String = "http://www.bratislavskakozlovna.sk/"

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts("ul.food-menu li") map SimpleDish toSeq
  }
}
