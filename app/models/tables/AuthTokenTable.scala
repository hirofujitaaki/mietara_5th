package models.tables

import org.joda.time._ //?? for what?
// import slick.driver.PostgresDriver.api._
import slick.driver.JdbcProfile // for H2
import slick.lifted.ProvenShape

//?? why the hell has to make this models twice?
case class DbAuthToken(
  id: String, //?? why not the type of UUID?
  userID: String, //?? why not UUID?
  expiry: String  //?? why not DateTime?
)

class AuthTokenTable(tag: Tag) extends Table[DbAuthToken](tag, "auth_tokens") {

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def userID: Rep[String] = column[String]("user_id")

  def expiry: Rep[String] = column[String]("expiry")

  override def * : ProvenShape[DbAuthToken] = (id, userID, expiry) <> (DbAuthToken.tupled, DbAuthToken.unapply)

}