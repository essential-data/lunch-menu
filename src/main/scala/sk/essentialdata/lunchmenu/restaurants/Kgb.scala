package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

import scala.language.postfixOps

/**
  * @author miso
  */
case object Kgb extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "https://www.zomato.com/sk/bratislava/kgb-star%C3%A9-mesto-bratislava-i/denn%C3%A9-menu"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenu = doc >> elementList("div.tmi-group") filter {_.text contains currentWeekDay}
    todayMenu flatMap {_ >> texts("div.tmi-daily") map SimpleDish toSeq} filterNot {_.name == ""}
  }
}
