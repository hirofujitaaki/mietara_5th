/**
 * Defining the schema
 * Tables represent mapping between scala data types and the tables in database data.
 */
package models.tables

import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

/**
 * The first job is to tell Slick what tables you have in the database
 * and how to map them onto Scala values and types.
 */

case class DbBlog(
  id: Option[Long],
  title: String,
  content: String,
  userID: String,
  createdAt: String
/**
 * There is nothing special about the default value, 0L, -- it doesn't signify anything
 * in particualr. It is the O.AutoInc option that determines the behavior of +=.
 */
)

// defines the class BlogTable
class BlogTable(tag: Tag) extends Table[DbBlog](tag, "blogs") {

  def id: Rep[Option[Long]] = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)

  def title: Rep[String] = column[String]("title")

  def content: Rep[String] = column[String]("content")

  def userID: Rep[String] = column[String]("uesr_id")

  def createdAt: Rep[String] = column[String]("created_at")

  override def * : ProvenShape[DbBlog] = (id, title, content, userID, createdAt) <> (DbBlog.tupled, DbBlog.unapply)

  /**
   * There's a snippet private val blogs = TableQuery[BlogTable] in BlogDAOImpl.scala
   * and all of this code works like blogs = SQL select * from blogs
   */

}
