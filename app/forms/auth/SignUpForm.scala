package forms.auth

import play.api.data.Form
import play.api.data.Forms._
import java.sql.Date

/**
 * The form which handles the sign up process.
 * A standalone object which works like a namespace.
 * Now you can use it in a view file like (signInForm: Form[forms.SignInForm.Data])
 */
object SignUpForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "birthday" -> sqlDate // format YYYY-MM-DD. optionally take pattern and timeZone
    )(Data.apply)(Data.unapply)
  )

  /**
   * The form data.
   *
   * @param firstName The first name of a user.
   * @param lastName The last name of a user.
   * @param email The email of the user.
   * @param password The password of the user.
   * @param birthday The birthday of the user.
   */
  case class Data(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    birthday: Date)
}
