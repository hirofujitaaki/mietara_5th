package models.tables

import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

/**
 * The first job is to tell Slick what tables you have in the database
 * and how to map them onto Scala values and types.
 */

case class DbBlog(
  id: Int,
  title: Option[String],
  content: Option[String],
  userID: String,
  createdAt: Option[String],
  updatedAt: Option[String]
)

class BlogTable(tag: Tag) extends Table[DbBlog](tag, "blogs") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def title: Rep[Option[String]] = column[Option[String]]("title")

  def content: Rep[Option[String]] = column[Option[String]]("content")

  def userID: Rep[String] = column[String]("uesr_id")

  def createdAt: Rep[Option[String]] = column[Option[String]]("created_at")

  def updatedAt: Rep[Option[String]] = column[Option[String]]("updated_at")

  override def * : ProvenShape[DbBlog] = (id, title, content, userID, createdAt, updatedAt) <> (DbBlog.tupled, DbBlog.unapply)

}
