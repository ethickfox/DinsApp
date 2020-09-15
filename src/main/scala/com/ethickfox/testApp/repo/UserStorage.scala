package com.ethickfox.testApp.repo

import scala.concurrent.Future
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._
import com.ethickfox.testApp.Main.{db, log}

//объект для взаимодействия с таблицей пользователей из базы данных
object UserStorage {
  //переменная, хранящая  структуру таблицы из бд
  val users = TableQuery[UserTable]
  //получение всех пользователей из бд
  def getUsers: Future[Seq[User]] =  db.run(users.result)
  //получение пользователя из бд по id
  def getUserById(id: Long): Future[Seq[User]] = db.run(users.filter(_.id === id).result)
  //удаление пользователя из бд по id
  def removeUserById(id: Long): Future[Int] = db.run(users.filter(_.id === id).delete)
  //изменение пользователя из бд по id
  def updateUserById(id: Long, user: User): Future[Int] = {
    db.run(
      users.filter(_.id === id)
        .map(p => (p.firstName, p.lastName, p.birthday, p.address))
        .update(user.firstName, user.lastName, user.birthday, user.address)
    )
  }
  //создание пользователя в бд
  def createUser(user: User): Future[Int] = {
    db.run(users.map(p => (p.firstName, p.lastName, p.birthday, p.address)) +=
      (user.firstName, user.lastName, user.birthday, user.address))
  }
}