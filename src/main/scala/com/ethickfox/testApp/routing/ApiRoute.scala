package com.ethickfox.testApp.routing

import akka.http.scaladsl.server.Directives.{as, complete, concat, delete, entity, get, onSuccess, path, pathEnd, pathPrefix, post, put, redirect}
import scala.concurrent.Await
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import com.ethickfox.testApp.Main.log
import akka.http.scaladsl.server.Directives._
import com.ethickfox.testApp.repo.User
import com.ethickfox.testApp.repo.UserStorage.{createUser, getUserById, getUsers, removeUserById, updateUserById}
import com.google.gson.Gson
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import scala.concurrent.duration.{Duration, DurationInt}


trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val clientJsonFormat: RootJsonFormat[User] = jsonFormat5(User)
}

class ApiRoute extends JsonSupport{
  def apiRoute:Route = {
    pathPrefix("api") {
      path("users") {
        val json = new Gson().toJson(Await.result(getUsers, Duration.Inf))
        log.info(json)
        get {
          complete(json)
        }
      } ~
      pathPrefix("user") {
        concat(
          pathEnd {
            redirect("/api/users", StatusCodes.PermanentRedirect)
          },
          pathPrefix("delete"){
            path(IntNumber) {
              name =>
                delete {
                  onSuccess(removeUserById(name)) { performed =>
                    complete(new Gson().toJson(StatusCodes.OK))
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
                onSuccess(updateUserById(user.id, user)) { performed =>
                  complete(new Gson().toJson(StatusCodes.OK))
                }
              }
              }
            }
          },
          path("create") {
            post {
              entity(as[User]) { user => {
                onSuccess(createUser(user)) { performed =>
                  complete(new Gson().toJson(StatusCodes.OK))
                }
              }
              }
            }
          }
        )
      }
    }
  }
}
