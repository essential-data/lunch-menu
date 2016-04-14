package sk.essentialdata.lunchmenu.search

import com.typesafe.config.{Config, ConfigFactory}
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import sk.essentialdata.lunchmenu.search.servlets.Search

/**
  * @author miso
  */
trait SearchJettyBinder {
  def bindSearchServlet() = {
    val config: Config = ConfigFactory.load()
    val server = new Server(config.getInt("search.port"))
    val context = new ServletContextHandler
    context.setContextPath("/")
    server.setHandler(context)
    context.addServlet(classOf[Search], "/*")
    server.start()
  }
}
