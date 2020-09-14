package com.ethickfox.testApp.repo

import java.sql.Date

import scala.concurrent.Future
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._
import com.ethickfox.testApp.Main.{db, log}


object UserStorage {
  val users = TableQuery[UserTable]
  def getUsers: Future[Seq[User]] =  db.run(users.result)
  def getUserById(id: Long): Future[Seq[User]] = db.run(users.filter(_.id === id).result)
  def removeUserById(id: Long): Future[Int] = db.run(users.filter(_.id === id).delete)
  def updateUserById(id: Long, user: User): Future[Int] = {
    db.run(
      users.filter(_.id === id)
        .map(p => (p.firstName, p.lastName, p.birthday, p.address))
        .update(user.firstName, user.lastName, user.birthday, user.address)
    )
  }
  def createUser(user: User): Future[Int] = {
    db.run(users.map(p => (p.firstName, p.lastName, p.birthday, p.address)) +=
      (user.firstName, user.lastName, user.birthday, user.address))
  }
}