package com.ethickfox.testApp.repo

import slick.jdbc.H2Profile.api._

// класс пользователя, соответствующий хранящимся в таблице
class UserTable(tag: Tag) extends Table[User](tag, None, "user") {
//переопределение *
  override def * = (id, firstName, lastName, birthday, address) <> (User.tupled, User.unapply)
// поле id
  val id = column[Long]("Id", O.AutoInc, O.PrimaryKey)
//  поле имя пользователя
  val firstName = column[String]("FirstName")
//  поле фамилия пользователя
  val lastName = column[String]("LastName")
//  поле  день рождения пользователя
  val birthday  = column[String]("Birthday")
//  поле адрес пользователя
  val address = column[String]("Address")
}
// класс образец для моделирования объекта пользователя, соответствующего хранящемуся в таблице
case class User(id:Long, firstName:String, lastName:String, birthday:String, address:String)
