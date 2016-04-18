package sk.essentialdata.lunchmenu

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import sk.essentialdata.lunchmenu.search.SearchJettyBinder

/**
  * @author miso
  */
object Boot extends scala.App with IndexingManager with SearchJettyBinder {
  bindSearchServlet()

  while(true) {
    Restaurant.all.map(indexRestaurant)
    val isWeekDay = DateTime.now().getDayOfWeek < 5
    val hour = DateTime.now().getHourOfDay
    val isPeakTime = isWeekDay && (hour >= 8) && (hour <= 14)
    val intervalMinutes = ConfigFactory.load().getInt(s"indexing.intervalMinutes.${if (isPeakTime)"peakTime"; else "otherwise"}")
    println(s"Poling with interval $intervalMinutes minutes")
    Thread.sleep(intervalMinutes * 60000)
  }
}


