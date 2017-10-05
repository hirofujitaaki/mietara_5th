package models.services

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.util.Clock
import models.Blog
import models.User
import models.daos.BlogDAO
import org.joda.time.DateTimeZone
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Handles actions to auth tokens.
 *
 * @param authTokenDAO The auth token DAO implementation.
 * @param clock The clock instance.
 */
class BlogServiceImpl @Inject() (blogDAO: BlogDAO, clock: Clock) extends BlogService {

  /**
   * Creates a new post and saves it in the backing store.
   *
   * @param title The title of the post.
   * @param content The content of the post.
   * @param userID The user by whom the post is created.
   */
  def create(title: String, content: String, user: User): Future[Blog] = {
    blogDAO.save(Blog(
      id = None,
      title = title,
      content = content,
      userID = user.userID,
      createdAt = clock.now
    ))
  }
}

