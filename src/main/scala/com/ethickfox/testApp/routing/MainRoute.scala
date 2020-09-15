package com.ethickfox.testApp.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.model.StatusCodes.{PermanentRedirect, Redirection}
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

import scala.concurrent._
import scala.concurrent.duration.{Duration, DurationInt}



class MainRoute {
  def mainRoute:Route = {
    def redirectSingleSlash =
      pathSingleSlash {
        get {
          redirect("index.html", PermanentRedirect)
        }
      }

    getFromResourceDirectory("frontend") ~ redirectSingleSlash
  }
}
