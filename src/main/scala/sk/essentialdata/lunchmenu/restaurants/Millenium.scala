package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

/**
  * @author miso
  */
case object Millenium extends Restaurant with SelectingDayOfWeek {
  def url: String = "http://www.mileniumcafe.sk/denne-menu"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    val iterator: Iterator[Iterable[String]] = doc >> texts("#denne-menu-vypis h3,#denne-menu-vypis h4,#denne-menu-vypis p") dropWhile {!_.contains(currentWeekDay)} drop 1 takeWhile {!_.contains(tomorrowWeekDay)} grouped 2
    iterator.toList map {_.filterNot{_.isEmpty}.mkString(", ")} map SimpleDish
  }
}
