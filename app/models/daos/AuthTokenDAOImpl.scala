package models.daos

import java.util.UUID
import javax.inject.Inject

import models.AuthToken
import models.daos.AuthTokenDAOImpl._
import models.tables.{ AuthTokenTable, DbAuthToken }
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the [[AuthToken]] object.
 */
class AuthTokenDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends AuthTokenDAO {

  val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
  val db: JdbcBackend#DatabaseDef = dbConfig.db

  import dbConfig.driver.api._

  /**
   * Finds a token by its ID.
   *
   * @param id The unique token ID.
   * @return The found token or None if no token for the given ID could be found.
   */
  def find(id: UUID): Future[Option[AuthToken]] = {
    db.run(tokens.filter(_.id === id.toString).result.headOption).map { resultOption =>
      resultOption.map {
        dbToken => AuthToken(UUID.fromString(dbToken.id), UUID.fromString(dbToken.userID), DateTime.parse(dbToken.expiry))
      }
    }
  }

  /**
   * Saves a token.
   *
   * @param token The token to save.
   * @return The saved token.
   */
  def save(token: AuthToken): Future[AuthToken] = {
    db.run((
      tokens returning tokens.map(_.id) into ((token, id) => token.copy(id))
    ) += DbAuthToken(token.id.toString, token.userID.toString, token.expiry.toString)).map { _ =>
      token
      /**
       * DB usually returns Int, which represents the rows effected. This behavior can be changed
       * with the returning method where you specify the columnS to be returned.
       * val userId = (users returning users.map(_.id)) += User(None, "Steve", "Zeiger")
       *
       * You can follow th returning method with the into method to map the inserted values and
       * the generated keys (specified in returning) to desired value. Here is an example of
       * using this feature to return an object with an updated it:
       * val userWithId = (users returning users.map(_.id) into ((user, id) =>
       *    user.copy(id=Some(id)))
       * ) += User(None, "Steve", "Zeiger")
       */
    }
  }

  /**
   * Removes the token for the given ID.
   *
   * @param id The ID for which the token should be removed.
   * @return A future to wait for the process to be completed.
   */
  def remove(id: UUID): Future[Int] = {
    db.run(tokens.filter(_.id === id.toString).delete)
  }
}

/**
 * The companion object.
 */
object AuthTokenDAOImpl {

  /**
   * The list of tokens.
   */
  private val tokens = TableQuery[AuthTokenTable]

}
