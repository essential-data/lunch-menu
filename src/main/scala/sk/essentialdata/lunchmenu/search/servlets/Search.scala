package sk.essentialdata.lunchmenu.search.servlets

import org.scalatra.ScalatraServlet
import sk.essentialdata.lunchmenu.solr.Solr

/**
  * @author miso
  */
class Search extends ScalatraServlet with Solr {
  before() {
    contentType = "text/html"
  }
  get("/") {
    <table border="1">
      {for (menu <- displayTodayMenu()) yield {
        val headOption = menu._2.headOption
        for (lunch <- menu._2) yield <tr>{if(headOption.contains(lunch)) <td rowspan={menu._2.size.toString}>{menu._1}</td>}<td>{lunch}</td></tr>
      } }
    </table>
  }
}
