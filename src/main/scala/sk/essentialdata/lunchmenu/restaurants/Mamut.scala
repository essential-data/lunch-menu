package sk.essentialdata.lunchmenu.restaurants

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import sk.essentialdata.lunchmenu._

/**
  * @author miso
  */
case object Mamut extends Restaurant with SelectingDayOfWeek[String] {
  def url: String = "http://www.mamutpub.sk/web/jedalny-listok"

  def weekDays: Seq[String] = Seq("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok")

  override def parse(doc: Document): Seq[Dish] = {

    val todayMenu = doc >> elementList("#side_column .contentpaneopen td > div") filter {_.text.startsWith(currentWeekDay)}

    val todayDishes = todayMenu flatMap {_ >> elementList("div")} filter {_.children.isEmpty} map {_.text}

    val soups = todayDishes filter {_.startsWith("P")} map Soup

    val mainDishes = todayDishes filter {!_.startsWith("P")} map MainDish

    soups ++ mainDishes
  }
}
