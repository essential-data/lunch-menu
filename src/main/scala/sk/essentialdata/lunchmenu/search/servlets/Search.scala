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

  get("/lunch-menu/") {
    contentType = "text/html"
    renderHtmlMenu(displayTodayMenu())
  }

  get("/lunch-menu/keto") {
    contentType = "text/html"
    renderHtmlMenu(displayTodayMenu(Some(new BlackList("keto")))).toString()
  }

  get("/lunch-menu/json") {
    contentType = formats("json")
    displayTodayMenu()
  }

  private def renderHtmlMenu(todayMenu: Map[String, Seq[String]]): String =
    "<!DOCTYPE html>" + menuToHtml(todayMenu)


  private def menuToHtml(todayMenu: Map[String, Seq[String]]): Elem =
    <html lang="en">
      <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Čo dobré na obed?</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />
      </head>
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
        { menuToHtmlTable(todayMenu) }
      </body>
    </html>

  private def menuToHtmlTable(todayMenu: Map[String, Seq[String]]): Elem =
    <table class="table">
      {for (menu <- todayMenu) yield {
      val headOption = menu._2.headOption
      for (lunch <- menu._2) yield <tr>{if(headOption.contains(lunch)) <td rowspan={menu._2.size.toString}>{menu._1}</td>}<td>{Unparsed(lunch)}</td></tr>
    } }
    </table>
}
