package models.services

import java.util.UUID
import models.Blog
import models.User

import scala.concurrent.Future

/**
 * Handles actions to blogs.
 */
trait BlogService {

  /**
   * Retrieves blogs created by a specific user.
   *
   * @param user the user who created the retrieving posts
   * @return The retrieved posts or None if no posts are retrieved for the given user.
   */
  def retrieve(user: User): Future[Option[Seq[Blog]]]

  /**
   * Creates a new post and saves it in the backing store.
   *
   * @param title The title of the post.
   * @param content The content of the post.
   * @param userID The user by whom the post is created.
   */
  def create(title: String, content: String, user: User): Future[Blog]

}
