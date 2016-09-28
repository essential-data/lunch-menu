package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Suvlaki extends Restaurant with FacebookFeed with SelectingDayOfWeek[Seq[String]] {
  def url: String = "https://www.facebook.com/Bistro-Suvlaki-867453936608481"

  def weekDays = Seq(Seq("PONDELOK"), Seq("UTOROK"), Seq("STREDA", "STREDU"), Seq("ŠTVRTOK"), Seq("PIATOK"))

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenuOpt: Option[String] = doc.latestPosts(2) find { post =>
      currentWeekDay.exists(post.contains)
    }
    val dishes: Seq[SimpleDish] = todayMenuOpt map {_.split("""Gluten free\)|\d/|kali orexi""").map(_.trim).drop(1).dropRight(1).map(SimpleDish).toSeq} getOrElse Seq.empty
    dishes
  }
}
