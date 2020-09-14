package com.ethickfox.testApp.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.model.StatusCodes.Redirection
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import com.ethickfox.testApp.Main.log
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.ethickfox.testApp.repo.{User, UserStorage}
import com.ethickfox.testApp.repo.UserStorage.{createUser, getUserById, getUsers, removeUserById, updateUserById}
import com.google.gson.Gson
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol.jsonFormat5
//import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val clientJsonFormat: RootJsonFormat[User] = jsonFormat5(User)
}

class MainRoute extends JsonSupport{
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
        pathPrefix("user") {
          concat(
            pathEnd {
              redirect("/api/users", StatusCodes.PermanentRedirect)
            },
            pathPrefix("delete") {
              path(IntNumber) {
                name =>
                  delete {
                    onSuccess(removeUserById(name)) { performed =>
                      complete(StatusCodes.OK)
                    }
                  }
              }
            },
            path(IntNumber) { int =>
              complete(new Gson().toJson(Await.result(getUserById(int), 1.second)))
            },
            path("update") {
              put {
                entity(as[User]) { user => {
                  println(user)
                  onSuccess(updateUserById(user.id, user)) { performed =>
                    complete(StatusCodes.OK)
                  }
                }
                }
              }
            },
            path("create") {
              post {
                entity(as[User]) { user => {
                  println(user)
                  onSuccess(createUser(user)) { performed =>
                    complete(StatusCodes.OK)
                  }
                }
                }
              }
            }
          )
        }
    }
}
