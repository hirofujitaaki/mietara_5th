package models.tables

import org.joda.time._ //?? for what?
import slick.driver.PostgresDriver.api._
/**
 *  // import slick.driver.H2Driver.api._
 *  causes SlickException: This DBMS allows only a single AutoInc column to
 *  be returned from an INSERT. but the type of the id column in AuthToken models is
 *  actually UUID. so applying O.AutoInc would mismatch with DB.
 */
import slick.lifted.ProvenShape

case class DbAuthToken(
  id: String,
  userID: String,
  expiry: String
)

class AuthTokenTable(tag: Tag) extends Table[DbAuthToken](tag, "auth_tokens") {

  def id: Rep[String] = column[String]("id", O.PrimaryKey) // can't add O.AutoInc here.

  def userID: Rep[String] = column[String]("user_id")

  def expiry: Rep[String] = column[String]("expiry")

  override def * : ProvenShape[DbAuthToken] = (id, userID, expiry) <> (DbAuthToken.tupled, DbAuthToken.unapply)

}
