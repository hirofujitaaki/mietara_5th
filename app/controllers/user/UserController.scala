package controllers.user

import java.util.UUID
import javax.inject.Inject
import java.time._

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers._
import controllers.WebJarAssets
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
class UserController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  blogService: BlogService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Views `a user's posts` page.
   *
   * @param userID: the ID of the target user to retrieve.
   * @return The profile and posts of the target user.
   */
  def view(userID: UUID): Action[AnyContent] = silhouette.UserAwareAction.async { implicit request =>
    val targetUser: Future[User] = userService.retrieve(userID).map { _.get }

    targetUser.flatMap { targetUser =>
      blogService.retrieve(targetUser).map { targetBlogSeq =>
        Ok(views.html.user(request.identity, targetUser, targetBlogSeq))
      }
    }
  }
}

