package com.ethickfox.testApp.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.model.StatusCodes.Redirection
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import com.ethickfox.testApp.Main.log
import akka.http.scaladsl.server.Directives._
import com.ethickfox.testApp.repo.User
import com.ethickfox.testApp.repo.UserStorage.{getUserById, getUsers}
import com.google.gson.Gson

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class MainRoute {
  val api:Route =
    path(""){
      getFromResource("static/index.html")
    }~
    pathPrefix("api") {
        path("users") {
          val json = new Gson().toJson(Await.result(getUsers, Duration.Inf))
          log.info(json)
          get {
            complete(json)
          }
        }~
        pathPrefix("user"){
          concat(
            pathEnd {
              redirect("/api/users",StatusCodes.PermanentRedirect)
            },
            path(IntNumber) { int =>
              complete(new Gson().toJson(Await.result(getUserById(int),Duration.Inf)))
            }
          )
        }
    }
}
