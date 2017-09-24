package controllers.auth

import java.net.URLDecoder
import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api._ // { LoginInfo included }
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import controllers.{ WebJarAssets, auth, pages }
import models.services.{ AuthTokenService, UserService }
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.mailer.{ Email, MailerClient }
import play.api.mvc.{ Action, AnyContent, Controller, Result }
import play.api.Configuration
import utils.auth.DefaultEnv

import scala.concurrent.Future
import scala.language.postfixOps

/**
 * The `Activate Account` controller.
 *
 * @param messagesApi      The Play messages API.
 * @param silhouette       The Silhouette stack.
 * @param userService      The user service implementation.
 * @param authTokenService The auth token service implementation.
 * @param configuration
 * @param mailerClient     The mailer client.
 * @param webJarAssets     The WebJar assets locator.
 */
class ActivateAccountController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  authTokenService: AuthTokenService,
  configuration: Configuration,
  mailerClient: MailerClient,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Sends an account activation email to the user with the given email.
   *
   * @param email The email address of the user to send the activation mail to.
   * @return The result to display.
   */
  def send(email: String): Action[AnyContent] = silhouette.UnsecuredAction.async { implicit request =>
    val decodedEmail = URLDecoder.decode(email, "UTF-8")
    val loginInfo = LoginInfo(CredentialsProvider.ID, decodedEmail)
    val result = Redirect(auth.routes.SignInController.view()).flashing("info" -> Messages("activation.email.sent", decodedEmail))

    userService.retrieve(loginInfo).flatMap {
      case Some(user) if !user.activated =>
        authTokenService.create(user.userID).map { authToken =>
          val url = auth.routes.ActivateAccountController.activate(authToken.id).absoluteURL()

          mailerClient.send(Email(
            subject = Messages("email.activate.account.subject"),
            from = Messages("email.from"),
            to = Seq(decodedEmail),
            bodyText = Some(views.txt.emails.activateAccount(user, url).body),
            bodyHtml = Some(views.html.emails.activateAccount(user, url).body)
          ))
          result
        }
      case None => Future.successful(result)
    }
  }

  /**
   * Activates an account.
   *
   * @param token The token to identify a user.
   * @return The result to display.
   */
  def activate(token: UUID): Action[AnyContent] = silhouette.UnsecuredAction.async { implicit request =>
    //authTokenService.validate(id: UUID): Future[Option[AuthToken]]
    authTokenService.validate(token).flatMap {
      // Option[AuthToken] => userService.retrieve(id: UUID): Future[Option[User]]
      case Some(authToken) => userService.retrieve(authToken.userID: UUID).flatMap {
        case Some(user) if user.loginInfo.providerID == CredentialsProvider.ID =>
          // userService.save(user: User): Future[User]
          userService.save(user.copy(activated = true)).flatMap { user =>
            val result = Redirect(pages.routes.ApplicationController.index()).flashing("success" -> Messages("account.activated")): Result
            // userService.retrieve(loginInfo: LoginInfo): Future[Option[User]]
            userService.retrieve(user.loginInfo).flatMap {
              case Some(user) =>
                //returns Future[utils.auth.DefaultEnv#A#]
                silhouette.env.authenticatorService.create(user.loginInfo).flatMap {
                  authenticator =>
                    silhouette.env.eventBus.publish(LoginEvent(user, request))
                    // returns Future[utils.auth.DefaultEnv#Env#Value]
                    silhouette.env.authenticatorService.init(authenticator).flatMap { v =>
                      // returns Future[AuthenticatorResult]
                      silhouette.env.authenticatorService.embed(v, result)
                    }
                }
              case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
            }
          }
        case _ => Future.successful(Redirect(auth.routes.SignInController.view()).flashing("error" -> Messages("invalid.activation.link"))): Future[Result]
      }
      case None => Future.successful(Redirect(auth.routes.SignInController.view()).flashing("error" -> Messages("invalid.activation.link"))): Future[Result]
    }
  }
}
