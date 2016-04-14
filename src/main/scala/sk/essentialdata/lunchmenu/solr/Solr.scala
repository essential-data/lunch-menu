package sk.essentialdata.lunchmenu.solr

import java.math.BigInteger

import com.typesafe.config.ConfigFactory
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.common.SolrInputDocument
import org.apache.solr.common.params.{GroupParams, HighlightParams}
import sk.essentialdata.lunchmenu.Restaurant
import sk.essentialdata.lunchmenu.highlighting.BlackList

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

/**
  * @author miso
  */
trait Solr {

  val solrCore = "lunch"
  val solrBaseUrl = ConfigFactory.load().getString("solr.url.base")
  val solrUrl = s"$solrBaseUrl/$solrCore"
  val solrClient = new HttpSolrClient(solrUrl)

  def addDocs(docs: Seq[SolrInputDocument], restaurant: Restaurant): Unit = {
    Try {
      val response = solrClient.add(docs)
      solrClient.commit()
      response
    } match {
      case Success(response) => println(s"Indexed ${restaurant.name} in ${response.getElapsedTime}ms")
      case Failure(t) => println(s"Indexing failure: ${t.getMessage}")
    }

  }

  def displayTodayMenu(blacklistOpt: Option[BlackList] = None): Map[String, Seq[String]] = {

    val solrQuery = new SolrQuery("*")
      .addFilterQuery("day:NOW/DATE")
      .setRows(1000)
    solrQuery
      .set(GroupParams.GROUP, "true")
      .set(GroupParams.GROUP_FIELD, "restaurant_name")
      .set(GroupParams.GROUP_TOTAL_COUNT, "true")
      .set(GroupParams.GROUP_LIMIT, 10)
    blacklistOpt.map(_.meals) map {blacklistedMeals =>
      solrQuery
        .setHighlight(true)
        .setParam(HighlightParams.Q, blacklistedMeals.mkString(" "))
        .addHighlightField("lunch")
        .setHighlightFragsize(0)
        .setHighlightSnippets(1)
        .setHighlightSimplePre("<mark>")
        .setHighlightSimplePost("</mark>")
    }
    Try(solrClient.query(solrQuery)) match {
      case Success(response) =>
        response.getGroupResponse.getValues.headOption match {
          case Some(groupCommand) =>
            val numGroups = BigInteger.valueOf(groupCommand.getNGroups.toLong)
            println(s"Result contains $numGroups groups")
            groupCommand.getValues.map{group =>
              val restaurant = group.getGroupValue
              val lunches = group.getResult map { solrDocument =>

                val lunch = solrDocument("lunch").toString
                val hash = solrDocument("hash")

                val highlightedLunchOpt = Option(response.getHighlighting) match {
                  case Some(highlighting) =>
                    Option(highlighting.get(hash)) match {
                      case Some(highlightingMap) => Option(highlightingMap.get("lunch")) match {
                        case Some(snippets) => Option(snippets.get(0)) match {
                          case Some(snippet) => Some(snippet)
                          case None => println(s"Lunch $hash has zero snippets in Solr highlight response"); None
                        }
                        case None => println(s"Lunch $hash matched but no lunch is in Solr highlight response"); None
                      }
                      case None => println(s"Lunch $hash matched, but has no occurence in highlight section of Solr response"); None
                    }
                  case None => if (blacklistOpt.nonEmpty) println(s"No highlighting result"); None
                }


                highlightedLunchOpt.getOrElse(lunch)
              }
              restaurant -> lunches
            }.toMap
          case None => throw new RuntimeException(s"Solr did not respond with grouped result")
        }

      case Failure(t) =>
        println(s"Search failure: ${t.getMessage}")
        Map.empty
    }
  }
}
