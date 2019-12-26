package controllers

import javax.inject._
import jmx.JolokiaQueryService
import play.api.mvc._

@Singleton
class HomeController @Inject()(jolokiaQueryService: JolokiaQueryService, cc: MyControllerComponents) extends MyAbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    logger.debug("rendering index: ")
    val loggingEvents = jolokiaQueryService.loggingEvents
    Ok(views.html.index(loggingEvents))
  }
  
}
