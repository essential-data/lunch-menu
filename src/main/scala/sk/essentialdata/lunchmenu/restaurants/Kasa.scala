package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

import scala.language.postfixOps

/**
  * @author miso
  */
case object Kasa extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "https://restauracie.sme.sk/restauracia/salat-bar-kasa_7531-stare-mesto_2949/denne-menu"

  override def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    if ((doc >> allText(".dnesne_menu")).contains(currentWeekDay)) {
      val elements = doc >> element(".dnesne_menu") >> elementList(".jedlo_polozka")
      elements filter {el => (el >?> element("b")).isEmpty} map {el => SimpleDish(el.text)}
    } else Seq.empty

  }
}
