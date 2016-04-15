package sk.essentialdata.lunchmenu

import org.apache.solr.common.SolrInputDocument
import org.joda.time.DateTime
import sk.essentialdata.lunchmenu.solr.Solr

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author miso
  */
trait IndexingManager extends Solr {

  def indexRestaurant(restaurant: Restaurant) = {
    restaurant.download map { case dishes =>
      println(Console.YELLOW + s" ${restaurant.name} " + Console.RESET)
      indexDishes(dishes, restaurant)
    }
  }

  private def indexDishes(dishes: Seq[Dish], restaurant: Restaurant): Future[Any] = {
    val solrDocs = dishes map {dish =>
      println(s" ${dish.name}")
      val solrDoc: SolrInputDocument = new SolrInputDocument()
      solrDoc.addField("restaurant_name", restaurant.name)
      solrDoc.addField("restaurant_sort", restaurant.name)
      solrDoc.addField("restaurant_url", restaurant.url)
      solrDoc.addField("day", "NOW/DATE")
      solrDoc.addField("index_timestamp", "NOW")
      solrDoc.addField("lunch", dish.name)
      solrDoc.addField("hash", s"${restaurant.name} - ${DateTime.now().withTimeAtStartOfDay()} - ${dish.name}")
      solrDoc
    }
    if (solrDocs.isEmpty) {
      Future(println(s"Empty menu for ${restaurant.name}"))
    } else {
      Future(addDocs(solrDocs, restaurant))
    }
  }


}
