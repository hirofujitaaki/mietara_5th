package controllers.blog

import java.util.UUID
import javax.inject.Inject
import java.time._

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers._
import controllers.{ WebJarAssets, auth }
import forms.blog.BlogForm
import models.User
import models.services.{ AuthTokenService, UserService }
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{ Action, AnyContent, Controller }
import utils.auth.DefaultEnv

import scala.concurrent.Future

/**
 * The `Blog` controller.
 *
 */
class BlogController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  def view: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.blog.blog(BlogForm.form)))
  }

  def submit: Action[AnyContent] = ???
}

