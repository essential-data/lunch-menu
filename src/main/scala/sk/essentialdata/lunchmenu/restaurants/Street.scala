package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Street extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "http://www.street54.sk/#menu"

  def weekDays: Seq[String] = Seq("pondelok", "utorok", "streda", "štvrtok", "piatok")

  override def parse(doc: Document): Seq[Dish] = {
    val todayMenu = doc >> elementList(".one_half.parallax_scroll") filter { el =>
      val title = el >> allText(".ppb_menu_title")
      title.toLowerCase.contains(currentWeekDay)
    }

    val dishes = todayMenu flatMap {_ >> texts(".menu_content_classic") filter(_.length > 25)} map SimpleDish

    dishes
  }
}
