package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

import scala.language.postfixOps

/**
  * @author miso
  */
case object Obyvacka extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "http://restauracie.sme.sk/restauracia/obyvacka-cafe-restaurant_6367-stare-mesto_2949/denne-menu"

  override def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {
    if ((doc >> allText(".dnesne_menu")).contains(currentWeekDay)) {
      val menu = doc >> element(".dnesne_menu") >> texts(".jedlo_polozka")
      menu filter {x => x.contains("/") || x.take(1).forall(Character.isDigit)} map SimpleDish toSeq
    } else Seq.empty

  }
}
