package models.daos

import java.util.UUID
import javax.inject.Inject

import models.Blog
import models.User
import models.daos.BlogDAOImpl._
import models.tables.{ BlogTable, DbBlog }
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the [[Blog]] object.
 */
class BlogDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends BlogDAO {

  val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
  val db: JdbcBackend#DatabaseDef = dbConfig.db

  import dbConfig.driver.api._

  /**
   * Finds posts by the user
   *
   * @param user The user by whom the retrieving posts are created.
   * @return The found posts or None if no posts for the given user are found.
   */

  def find(user: User): Future[Seq[Blog]] = {
    val blogQuery: Query[BlogTable, DbBlog, Seq] = blogs.filter(_.userID === user.userID.toString).sortBy(_.createdAt.desc)

    db.run(blogQuery.result).map { dbBlogSeq =>
      dbBlogSeq.map { dbBlog => Blog(None, dbBlog.title, dbBlog.content, UUID.fromString(dbBlog.userID), DateTime.parse(dbBlog.createdAt))
      }
    }
  }

  /**
   * Saves a post.
   *
   * @param blog The blog to save.
   * @return The saved blog.
   */
  def save(blog: Blog): Future[Blog] = {
    db.run((
      blogs returning blogs.map(_.id) into ((blog, id) => blog.copy(id = id))
    ) += DbBlog(blog.id, blog.title, blog.content, blog.userID.toString, blog.createdAt.toString)).map { _ => blog }
  }
}

/**
 * The companion object.
 */
object BlogDAOImpl {

  /**
   * The list of blogs.
   */
  private val blogs = TableQuery[BlogTable]

}

