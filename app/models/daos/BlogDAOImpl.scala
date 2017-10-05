package models.daos

import java.util.UUID
import javax.inject.Inject

import models.Blog
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
   * Saves a post.
   *
   * @param blog The blog to save.
   * @return The saved blog.
   */
  def save(blog: Blog): Future[Int] = {
    db.run(
      blogs += DbBlog(blog.title, blog.content, blog.userID.toString, blog.createdAt.toString))
  }

  // db.run((
  //   blogs returning blogs.map(_.id) into ((blog, id) => blog.copy(id = id))
  // ) += DbBlog(blog.id, blog.title, blog.content, blog.userID.toString, blog.createdAt.toString)).map { _ => blog }
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

