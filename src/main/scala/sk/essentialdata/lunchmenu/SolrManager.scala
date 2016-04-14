package sk.essentialdata.lunchmenu

import com.typesafe.config.ConfigFactory
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.common.SolrInputDocument
import org.joda.time.DateTime

import scala.collection.JavaConversions._
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
trait SolrManager {

  val solrCore = "lunch"
  val solrBaseUrl = ConfigFactory.load().getString("solr.url.base")
  val solrUrl = s"$solrBaseUrl/$solrCore"
  val solrClient = new HttpSolrClient(solrUrl)

  def indexDishes(dishes: Seq[Dish], restaurant: Restaurant): Future[Any] = {
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
    Future {
      Try {
        val response = solrClient.add(solrDocs)
        solrClient.commit()
        response
      } match {
        case Success(response) => println(s"Indexed ${restaurant.name} in ${response.getElapsedTime}ms")
        case Failure(t) => println(s"Indexing failure: ${t.getMessage}")
      }
    }
  }


}
