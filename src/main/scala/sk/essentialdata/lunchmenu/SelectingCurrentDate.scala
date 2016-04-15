package sk.essentialdata.lunchmenu

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
  * @author miso
  */
trait SelectingCurrentDate {
  def currentDate(format: String) = DateTimeFormat.forPattern(format).print(DateTime.now())

}
