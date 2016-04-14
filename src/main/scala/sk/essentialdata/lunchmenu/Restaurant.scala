package sk.essentialdata.lunchmenu

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author miso
  */
trait Restaurant {
  def url: String
  def parse(doc: Document): Seq[Dish]

  def name = getClass.getSimpleName.replace("$", "")

  def download: Future[Seq[Dish]] = {
    val browser = JsoupBrowser()
    Future(browser.get(url)) map {doc => parse(doc)}
  }
}
