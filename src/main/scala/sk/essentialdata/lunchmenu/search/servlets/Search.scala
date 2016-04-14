package sk.essentialdata.lunchmenu.search.servlets

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json._
import sk.essentialdata.lunchmenu.highlighting.BlackList
import sk.essentialdata.lunchmenu.solr.Solr

import scala.xml.{Elem, Unparsed}

/**
  * @author miso
  */
class Search extends ScalatraServlet with JacksonJsonSupport with Solr {
  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/lunch-menu") {
    contentType = "text/html"
    menuToHtml(displayTodayMenu())
  }

  get("/lunch-menu/keto") {
    contentType = "text/html"
    menuToHtml(displayTodayMenu(Some(new BlackList("keto"))))
  }

  get("/lunch-menu/json") {
    contentType = formats("json")
    displayTodayMenu()
  }

  def menuToHtml(todayMenu: Map[String, Seq[String]]): Elem =
    <body>
      <style>
        table {{
          border-collapse: collapse;
        }}
        mark {{
          background-color: white;
          color: red;
        }}
      </style>
      <table border="1">
        {for (menu <- todayMenu) yield {
        val headOption = menu._2.headOption
        for (lunch <- menu._2) yield <tr>{if(headOption.contains(lunch)) <td rowspan={menu._2.size.toString}>{menu._1}</td>}<td>{Unparsed(lunch)}</td></tr>
      } }
      </table>
    </body>
}
