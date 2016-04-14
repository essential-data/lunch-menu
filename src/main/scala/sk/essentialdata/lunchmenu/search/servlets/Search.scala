package sk.essentialdata.lunchmenu.search.servlets

import org.scalatra.ScalatraServlet
import sk.essentialdata.lunchmenu.solr.Solr
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

/**
  * @author miso
  */
class Search extends ScalatraServlet with JacksonJsonSupport with Solr {
  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/lunch-menu") {
    contentType = "text/html"
    <table border="1">
      {for (menu <- displayTodayMenu()) yield {
        val headOption = menu._2.headOption
        for (lunch <- menu._2) yield <tr>{if(headOption.contains(lunch)) <td rowspan={menu._2.size.toString}>{menu._1}</td>}<td>{lunch}</td></tr>
      } }
    </table>
  }

  get("/lunch-menu/json") {
    contentType = formats("json")
    displayTodayMenu()
  }
}
