package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

import scala.language.postfixOps

/**
  * @author miso
  */
case object Kozlovna extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "https://menucka.sk/denne-menu/bratislava/bratislavska-kozlovna"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts("#restaurant-detail .row div") dropWhile {el => !el.contains(currentWeekDay)} dropWhile {el => el.contains(currentWeekDay)} takeWhile {!_.contains(tomorrowWeekDay)} filter (_.length > 15) map SimpleDish toSeq
  }
}
