package com.ethickfox.testApp.routing

import akka.http.scaladsl.model.StatusCodes.PermanentRedirect
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

// класс маршрутизации, отвечающий, за выдачу главной страницы и необходимых файлов ресурсов
class MainRoute {
  def mainRoute:Route = {
    def redirectSingleSlash = {
      //выдача index.html при запросе главной страницы
      pathSingleSlash {
        get {
          redirect("index.html", PermanentRedirect)
        }
      }
    }
    //выдача файлов из папки frontend, по соответствующим путям
    getFromResourceDirectory("frontend") ~ redirectSingleSlash
  }
}
