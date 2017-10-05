package forms.blog

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles blog posts.
 * A standalone object which works like a namespace.
 * Now you can use it in a view file like (blogForm: Form[forms.blog.BlogForm.Data])
 */

object BlogForm {

  /* The function of a Form is to transform form data into a bound instance of a case class.
   * The forms object defines the mapping method, which takes the names and constraints
   * of the form, and also takes two functions: an apply and an unapply function.
   */
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  /**
   * The blog data.
   *
   * @param title The title of a blog post
   * @param content The context of a blog post
   */
  case class Data(
    title: String,
    content: String
  )
}
