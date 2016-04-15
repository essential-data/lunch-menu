package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Suvlaki extends Restaurant with FacebookFeed with SelectingDayOfWeek {
  def url: String = "https://www.facebook.com/Bistro-Suvlaki-867453936608481"

  def weekDays: Seq[String] = Seq("PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenuOpt: Option[String] = doc.latestPosts(2) find {
      _.contains(currentWeekDay)
    }
    val dishes: Seq[SimpleDish] = todayMenuOpt map {_.split("""Gluten free\)|\d/|kali orexi""").map(_.trim).drop(1).dropRight(1).map(SimpleDish).toSeq} getOrElse Seq.empty
    dishes
  }
}
