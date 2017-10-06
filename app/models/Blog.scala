package models

import java.util.UUID
import org.joda.time.DateTime

/**
 * The blog object.
 *
 * @param id The blog ID.
 * @param title The title of the blog.
 * @param content The content of the blog.
 * @param userID The unique ID of the user by whom the blog is post.
 * @param createdAt the date-time the post is created.
 */

case class Blog(
  id: Option[Long],
  title: String,
  content: String,
  userID: UUID,
  createdAt: DateTime
)
