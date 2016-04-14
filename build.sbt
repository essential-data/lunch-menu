name := "lunch-menu"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper"     % "1.0.0",
  "org.apache.solr"   % "solr-solrj"        % "5.1.0",
  "org.scalatra"     %% "scalatra"          % "2.4.0",
  "javax.servlet"     % "javax.servlet-api" % "3.1.0",
  "org.eclipse.jetty" % "jetty-server"      % "9.3.9.M1",
  "org.eclipse.jetty" % "jetty-webapp"      % "9.3.9.M1"
)
