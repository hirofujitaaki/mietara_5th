package controllers.pages

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import controllers.{ WebJarAssets, pages }
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.{ Action, AnyContent, Controller }
import utils.auth.DefaultEnv

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class AccountController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Views the `Account` page.
   *
   * @return The result to display.
   */
  def view: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.account(request.identity)))
  }
}

