package sk.essentialdata.lunchmenu

import org.joda.time.DateTime

/**
  * @author miso
  */
trait SelectingDayOfWeek {
  def weekDays: Seq[String]

  def currentWeekDay = weekDays((DateTime.now().getDayOfWeek - 1) % weekDays.size)
  def tomorrowWeekDay = weekDays(DateTime.now().getDayOfWeek % weekDays.size)

}
