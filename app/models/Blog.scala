package models

import java.util.UUID

/**
 * The blog object.
 *
 * @param id The blog ID.
 * @param title The title of the blog.
 * @param content The content of the blog.
 * @param userID The unique ID of the user by whom the blog is post.
 * @param createdAt the date-time the post is created.
 * @param updatedAt the date-time the post is updated.
 */

case class Blog(
  id: Int,
  title: Option[String],
  content: Option[String],
  userID: UUID,
  createAt: Option[String],
  updatedAt: Option[String]
)
