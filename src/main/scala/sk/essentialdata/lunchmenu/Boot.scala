package sk.essentialdata.lunchmenu

import com.typesafe.config.ConfigFactory
import sk.essentialdata.lunchmenu.search.SearchJettyBinder

/**
  * @author miso
  */
object Boot extends scala.App with IndexingManager with SearchJettyBinder {
  bindSearchServlet()

  while(true) {
    Restaurant.all.map(indexRestaurant)
    Thread.sleep(ConfigFactory.load().getInt("indexing.intervalMinutes") * 60000)
  }
}


