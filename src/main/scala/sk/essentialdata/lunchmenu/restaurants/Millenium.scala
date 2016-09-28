package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Millenium extends Restaurant with SelectingDayOfWeek[String] with SelectingCurrentDate {
  def url: String = "http://www.mileniumcafe.sk/denne-menu"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    val iterator: Iterator[Iterable[String]] = doc >> texts("#denne-menu-vypis h3,#denne-menu-vypis h4,#denne-menu-vypis p") dropWhile {el => !el.contains(currentWeekDay) && !el.contains(currentDate("dd.MM.y")) } drop 1 takeWhile {el => !el.contains(tomorrowWeekDay) && !el.contains(tomorrowDate("dd.MM.y")) } grouped 2
    iterator.toList map {_.filterNot{_.isEmpty}.mkString(", ")} map SimpleDish
  }
}
