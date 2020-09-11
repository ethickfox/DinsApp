package com.ethickfox.testApp

import akka.actor.ActorSystem
import com.ethickfox.testApp.repo.User
import org.specs2.concurrent.FutureAwait

import scala.util.{Failure, Success}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import com.ethickfox.testApp.repo.UserStorage.{getUserbyName, getUsers}
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._
import com.ethickfox.testApp.repo.UserTable

import scala.concurrent._
import ExecutionContext.Implicits.global



object Main extends App {

  implicit val system: ActorSystem = ActorSystem("simple-http")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val log: LoggingAdapter = Logging(system, "main")

  val port = 8080
  val db = Database.forConfig("postgres")
  db.createSession()

  val bindingFuture =
    Http().bindAndHandle(
      path("users") {
        get {
          getUsers.onComplete{
            case Success(user) =>
              log.info(s"Users at all: $user")
            case Failure(exception) =>
              log.info(s"Creating user failed due to the exception: $exception")
          }
          getUserbyName("wqer").onComplete{
            case Success(user) =>
              log.info(s"User found by name: $user")
            case Failure(exception) =>
              log.info(s"Creating user failed due to the exception: $exception")

          }
          complete("ok")
        }
//        onSuccess(Future { "Ok" }) { extraction =>
//          complete(extraction)
//        }
      },
      "localhost", port)


  log.info(s"Server started at the port $port")
}