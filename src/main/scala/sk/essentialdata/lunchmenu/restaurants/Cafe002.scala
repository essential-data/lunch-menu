package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Cafe002 extends Restaurant with FacebookFeed with SelectingDayOfWeek {
  def url: String = "https://www.facebook.com/Cafe-002-39386032695"

  def weekDays: Seq[String] = Seq("PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenuOpt: Option[String] = doc.latestPosts(10 /* spammeri */) find {
      _.toUpperCase.contains(currentWeekDay)
    }
    val dishes: Seq[SimpleDish] = todayMenuOpt map {_.split(""" \d\.""").map(_.split("""\.\.\.""")(0).split(s"(?iu)$currentWeekDay").last.trim).map(SimpleDish).toSeq} getOrElse Seq.empty
    dishes
  }
}
