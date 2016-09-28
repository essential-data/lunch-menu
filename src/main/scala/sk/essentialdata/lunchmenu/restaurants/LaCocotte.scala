package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object LaCocotte extends Restaurant with FacebookFeed with SelectingDayOfWeek[Seq[String]] with StringSanitizingSupport {
  def url: String = "https://www.facebook.com/La-cocotte-bistro-396212950529936"

  def weekDays = Seq(Seq("pondelok"), Seq("utorok"), Seq("streda", "stredu"), Seq("štvrtok"), Seq("piatok"))

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenuOpt: Option[String] = doc.latestPosts(2) filter {
      _.contains("obed")
    } find { post =>
      currentWeekDay.exists(post.contains)
    }
    val dishOpt: Option[SimpleDish] = todayMenuOpt map {_.split("""Dobrú chuť""")(0).split("""podávame|servírujeme|pripravili|podávať|čakať"""").last.trimNonAlphabetic} map SimpleDish
    val dishes = dishOpt match {
      case Some(dish) => Seq(dish)
      case None => Seq.empty
    }
    dishes
  }
}
