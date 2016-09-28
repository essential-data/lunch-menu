package sk.essentialdata.lunchmenu

import org.joda.time.DateTime

/**
  * @author miso
  */
trait SelectingDayOfWeek[WeekDayType] {
  def weekDays: Seq[WeekDayType]

  def currentWeekDay: WeekDayType = weekDays((DateTime.now().getDayOfWeek - 1) % weekDays.size)
  def tomorrowWeekDay: WeekDayType = weekDays(DateTime.now().getDayOfWeek % weekDays.size)

}
