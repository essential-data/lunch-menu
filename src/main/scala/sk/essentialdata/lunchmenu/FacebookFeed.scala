package sk.essentialdata.lunchmenu

import net.ruippeixotog.scalascraper.model.Document
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

/**
  * @author miso
  */
trait FacebookFeed {
  implicit class RichDocument(doc: Document) {
    def latestPosts(num: Int): Seq[String] = (doc >> texts("div[role=article]")) take num toSeq
  }
}
