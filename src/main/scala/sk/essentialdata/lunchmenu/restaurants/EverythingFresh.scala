package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

/**
  * @author miso
  */
case object EverythingFresh extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "http://www.everythingfresh.sk/sk/Obedove-menu.html#kamenne"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    (doc >> element("div.lunch-menu") children) dropWhile {!_.text.contains(currentWeekDay)} drop 1 takeWhile {!_.text.contains(tomorrowWeekDay)} map {_.text} filter {_.size > 5} map SimpleDish toSeq
  }
}
