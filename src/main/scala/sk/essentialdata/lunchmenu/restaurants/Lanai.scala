package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

/**
  * @author miso
  */
case object Lanai extends Restaurant with SelectingDayOfWeek {
  def url: String = "http://www.lanai-cafe.sk/menu/"

  def weekDays: Seq[String] = Seq("PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK")

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts("p") dropWhile {!_.contains(currentWeekDay)} drop 1 takeWhile {!_.contains(tomorrowWeekDay)} map SimpleDish toSeq
  }
}
