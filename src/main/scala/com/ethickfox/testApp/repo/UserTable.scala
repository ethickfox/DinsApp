package com.ethickfox.testApp.repo
import java.sql.Date

import slick.jdbc.H2Profile.api._

class UserTable(tag: Tag) extends Table[User](tag, None, "user") {
  override def * = (id, firstName, lastName, birthday, address) <> (User.tupled, User.unapply)
  val id = column[Long]("Id", O.AutoInc, O.PrimaryKey)
  val firstName = column[String]("FirstName")
  val lastName = column[String]("LastName")
  val birthday  = column[String]("Birthday")
  val address = column[String]("Address")
}

case class User(id:Long, firstName:String, lastName:String, birthday:String, address:String)
