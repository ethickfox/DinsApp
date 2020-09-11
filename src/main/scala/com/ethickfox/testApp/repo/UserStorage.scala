package com.ethickfox.testApp.repo

import com.ethickfox.testApp.Main.db
import scala.concurrent.Future
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent._

object UserStorage {
  val users = TableQuery[UserTable]
  def getUsers: Future[Seq[User]] =  db.run(users.result)

  def getUserbyId(id: Long): Future[Seq[User]] = db.run(users.filter(_.id === id).result)

  def getUserbyName(firstName: String): Future[Seq[User]] = db.run(users.filter(_.firstName === firstName).result)
//  def saveUser(profile: User): Future[User] = {
//
//  }

}