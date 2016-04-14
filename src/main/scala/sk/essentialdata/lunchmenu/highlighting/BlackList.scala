package sk.essentialdata.lunchmenu.highlighting

import scala.io.Source._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

/**
  * @author miso
  */
class BlackList(name: String) {

  lazy val meals: Seq[String] = {
    val source = fromFile(s"blacklists/$name")
    Try(source.getLines) match {
      case Success(lines) =>
        lines toSeq
      case Failure(e) =>
        println(e.getMessage)
        Seq.empty
    }
  }

}
