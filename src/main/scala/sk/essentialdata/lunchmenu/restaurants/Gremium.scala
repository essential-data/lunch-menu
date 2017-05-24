package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu.{Dish, Restaurant, SelectingDayOfWeek, SimpleDish}

import scala.language.postfixOps

/**
  * @author miso
  */
case object Gremium extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "http://www.gremium.sk/denne-menu/"

  def weekDays: Seq[String] = Seq("PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK")

  override def parse(doc: Document): Seq[Dish] = {
    doc >> texts("ul.ArticleP li") dropWhile {!_.contains(currentWeekDay)} drop 1 takeWhile {!_.contains(tomorrowWeekDay)} filter {_.size > 5} map SimpleDish toSeq
  }
}
