package com.ethickfox.testApp.routing

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import com.ethickfox.testApp.Main.log
import akka.http.scaladsl.server.Directives._
import com.ethickfox.testApp.repo.User
import com.ethickfox.testApp.repo.UserStorage.getUsers
import com.google.gson.Gson

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class MainRoute {
  val api:Route =
//    path("", () ->
//      getFromResource("static/index.html")
//    )~
    path("users") {
      get{ complete(new Gson().toJson(Await.result(getUsers,Duration.Inf))) }
    } ~
    pathPrefix("user"){
        concat(
          pathEnd {
            complete("/users")
          },
          path(IntNumber) { int =>
            complete(if (int % 2 == 0) "even ball" else "odd ball")
          }
        )
    }

}
