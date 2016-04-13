package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Street extends Restaurant with SelectingDayOfWeek {
  def url: String = "http://stary.street54.sk/lunch-time/#menu"

  def weekDays: Seq[String] = Seq("Pondelok / Monday", "Utorok / Tuesday", "Streda / Wednesday", "Štvrtok / Thursday", "Piatok / Friday")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenu = doc >> elementList("#menu-dni") filter {el => (el >> text(".den")) == currentWeekDay}

    val soups = todayMenu flatMap {_ >> texts(".cela-polievka")} map Soup

    val mainDishes = todayMenu flatMap {_ >> texts(".cele-jedlo")} map MainDish

    soups ++ mainDishes
  }
}
