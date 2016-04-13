package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

/**
  * @author miso
  */
case object Flagship extends Restaurant with SelectingDayOfWeek {
  def url: String = "http://www.bratislavskarestauracia.sk/sk/denne-menu"

  def weekDays: Seq[String] = Seq("Pondelok / Monday", "Utorok / Tuesday", "Streda / Wednesday", "Štvrtok / Thursday", "Piatok / Friday")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenu = doc >> elementList("div.box") filter {el => (el >> text("div.row")) == currentWeekDay}
    todayMenu flatMap {_ >> texts("div.row") drop 1 map SimpleDish toSeq} filterNot {_.name == ""}
  }
}
