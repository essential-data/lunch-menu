package sk.essentialdata.lunchmenu

/**
  * @author miso
  */
sealed trait Dish {
  def name: String
}

case class SimpleDish(name: String) extends Dish
case class MainDish(name: String) extends Dish
case class Soup(name: String) extends Dish
