package controllers

import com.google.inject.ImplementedBy
import javax.inject.Inject
import play.api.http.FileMimeTypes
import play.api.i18n.{Langs, MessagesApi}
import play.api.inject.ApplicationLifecycle
import play.api.mvc._

@ImplementedBy(classOf[DefaultMyControllerComponents])
trait MyControllerComponents extends MessagesControllerComponents {
  def lifecycle: ApplicationLifecycle
}

case class DefaultMyControllerComponents @Inject() (
 messagesActionBuilder: MessagesActionBuilder,
 actionBuilder: DefaultActionBuilder,
 parsers: PlayBodyParsers,
 messagesApi: MessagesApi,
 langs: Langs,
 fileMimeTypes: FileMimeTypes,
 executionContext: scala.concurrent.ExecutionContext,
 lifecycle: ApplicationLifecycle
) extends MyControllerComponents
