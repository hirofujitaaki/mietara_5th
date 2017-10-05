package models.daos

import java.util.UUID

import models.Blog

import scala.concurrent.Future

/**
 * Give access to the [[Blog]] object.
 */
trait BlogDAO {

  /**
   * Saves a blog.
   *
   * @param blog The blog to save.
   * @return The saved blog.
   */
  def save(blog: Blog): Future[Blog]
}
