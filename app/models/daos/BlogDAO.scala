package models.daos

import java.util.UUID

import models.{ Blog, User }

import scala.concurrent.Future

/**
 * Give access to the [[Blog]] object.
 */
trait BlogDAO {

  /**
   * Finds posts by the user
   *
   * @param user The user by whom the retrieving posts are created.
   * @return The found posts or None if no posts for the given user are found.
   */
  def find(user: User): Future[Seq[Blog]]

  /**
   * Saves a blog.
   *
   * @param blog The blog to save.
   * @return The saved blog.
   */
  def save(blog: Blog): Future[Blog]
}
