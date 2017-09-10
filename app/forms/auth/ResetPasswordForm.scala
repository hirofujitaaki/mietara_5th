package forms.auth

import play.api.data.Forms._ //?? Is this in use? there's no mapping function used.
import play.api.data._

/**
 * The `Reset Password` form.
 */
object ResetPasswordForm {

  /**
   * A play framework form.
   */
  val form = Form(
    "password" -> nonEmptyText
  )
  //?? why there's no (Data.apply)(Data.unapply) part?
}

