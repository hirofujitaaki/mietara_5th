package modules

import com.google.inject.AbstractModule
import models.daos.{ AuthTokenDAO, AuthTokenDAOImpl, BlogDAO, BlogDAOImpl }
import models.services.{ AuthTokenService, AuthTokenServiceImpl, BlogService, BlogServiceImpl }
import net.codingwell.scalaguice.ScalaModule
import play.api.ApplicationLoader

/**
 * The base Guice module.
 */
class BaseModule extends AbstractModule with ScalaModule {

  /**
   * Configures the module.
   */
  def configure(): Unit = {
    ApplicationLoader

    bind[AuthTokenDAO].to[AuthTokenDAOImpl]
    bind[AuthTokenService].to[AuthTokenServiceImpl]
    bind[BlogDAO].to[BlogDAOImpl]
    bind[BlogService].to[BlogServiceImpl]
  }
}
