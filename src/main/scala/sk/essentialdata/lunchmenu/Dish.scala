package sk.essentialdata.lunchmenu

/**
  * @author miso
  */
sealed trait Dish {
  def name: String
  def sanitizedName: String = name.toLowerCase.replaceFirst(volumePattern, "").replaceFirst("^(jedlo )?\\d.\\s*", "").replaceFirst("(\\d.)?mennu[:,]?", "").replaceAll("\\s+", " ").replaceAll("\\s+,", ",").trim.capitalize
  def volumePattern: String = "[\\d.,/\\s\\\u00A0g]+[lg][\\s:]"
  def volume: Option[String] = volumePattern.r.findFirstIn(name)
}

case class SimpleDish(name: String) extends Dish
case class MainDish(name: String) extends Dish
case class Soup(name: String) extends Dish
