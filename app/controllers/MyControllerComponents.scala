package controllers

import com.google.inject.ImplementedBy
import javax.inject.Inject
import jmx.JMXServer
import play.api.http.FileMimeTypes
import play.api.i18n.{Langs, MessagesApi}
import play.api.mvc._

@ImplementedBy(classOf[DefaultMyControllerComponents])
trait MyControllerComponents extends MessagesControllerComponents {
  def jmxServer: JMXServer
}

case class DefaultMyControllerComponents @Inject() (
 messagesActionBuilder: MessagesActionBuilder,
 actionBuilder: DefaultActionBuilder,
 parsers: PlayBodyParsers,
 messagesApi: MessagesApi,
 langs: Langs,
 fileMimeTypes: FileMimeTypes,
 executionContext: scala.concurrent.ExecutionContext,
 jmxServer: JMXServer
) extends MyControllerComponents
