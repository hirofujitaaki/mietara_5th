package controllers.blog

import java.util.UUID
import javax.inject.Inject
import java.time._

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers._
import controllers.{ WebJarAssets, blog }
import forms.blog.BlogForm
import models.User
import models.Blog
import models.services.{ AuthTokenService, UserService, BlogService }
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{ Action, AnyContent, Controller }
import utils.auth.DefaultEnv

import scala.concurrent.Future

/**
 * The `Blog` controller.
 *
 * @param messagesApi            The Play messages API.
 * @param silhouette             The Silhouette stack.
 * @param userService            The user service implementation.
 * @param webJarAssets           The webjar assets implementation.
 */
class BlogController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  blogService: BlogService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Views the `Blog Post` page.
   *
   * @return The result to display.
   */
  def view: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    blogService.retrieve(request.identity).map { blogOption =>
      Ok(views.html.blog.blog(request.identity, BlogForm.form, blogOption))
    }
  }

  /**
   * Handles the submitted form.
   *
   * @return The result to display.
   */
  def submit: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    BlogForm.form.bindFromRequest.fold(
      form => blogService.retrieve(request.identity).map { blogOption =>
        BadRequest(views.html.blog.blog(request.identity, form, blogOption))
      },
      data => blogService.create(data.title, data.content, request.identity).map { res =>
        Redirect(blog.routes.BlogController.view())
      }
    )
  }
}

