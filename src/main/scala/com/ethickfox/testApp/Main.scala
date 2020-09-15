package com.ethickfox.testApp

import akka.actor.ActorSystem
import com.ethickfox.testApp.repo.User
import org.specs2.concurrent.FutureAwait

import scala.util.{Failure, Success}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._
import com.ethickfox.testApp.repo.UserTable
import com.ethickfox.testApp.routing.{ApiRoute, MainRoute}

import scala.concurrent._
import ExecutionContext.Implicits.global


object Main extends App {
  implicit val system: ActorSystem = ActorSystem("simple-http")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val log: LoggingAdapter = Logging(system, "main")
  val port = 8080
  val db = Database.forConfig("postgres")
  db.createSession()
  val routes: Route = new MainRoute().mainRoute ~ new ApiRoute().apiRoute
  val bindingFuture = Http().bindAndHandle(routes,"localhost", port)
  log.info(s"Server works. Port $port")
}