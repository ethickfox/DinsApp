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

//основной объект приложения
object Main extends App {
  //создание основного актора
  implicit val system: ActorSystem = ActorSystem("dinsApp")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //создание логгера
  implicit val log: LoggingAdapter = Logging(system, "main")
  val port = 8081
  //создание объекта, для взяаимодействия с базой данных и пременение параметров из application.config
  val db = Database.forConfig("postgres")
  //объединение всех маршрутов
  val routes: Route = new MainRoute().mainRoute ~ new ApiRoute().apiRoute
  // назначение порта и маршрутов
  val bindingFuture = Http().bindAndHandle(routes,"localhost", port)
  log.info(s"Server works. Port $port")
}