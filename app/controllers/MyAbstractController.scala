package controllers

import com.tersesystems.jmxbuilder.DynamicBean
import javax.inject.Inject
import javax.management.ObjectName
import logging.{LogbackLoggingComponent, LoggingComponent}
import org.slf4j.Logger
import play.api.Logging
import play.api.inject.ApplicationLifecycle
import play.api.mvc._
import javax.inject.Singleton

import scala.concurrent.Future

@Singleton
class MyAbstractController @Inject()(mcc: MyControllerComponents)
  extends AbstractController(mcc)
    with Logging with LogbackLoggingComponent with RegistrationComponent {

  override protected def logbackLogger: Logger = logger.logger

  registerWithJMX(new ObjectName(s"play:type=Controller,name=${getClass.getName}"), mcc.lifecycle)
}

trait RegistrationComponent {
  self: LoggingComponent =>

  protected def decorateBean(builder: DynamicBean.Builder): DynamicBean.Builder = builder

  protected def registerWithJMX(objectName: ObjectName, lifecycle: ApplicationLifecycle): Unit = {
    val bean: DynamicBean = decorateBean(LoggingComponent.jmxBuilder(this)).build
    registerBean(objectName, bean, lifecycle)
  }

  private def registerBean(objectName: ObjectName, bean: DynamicBean, lifecycle: ApplicationLifecycle): Unit = {
    import java.lang.management.ManagementFactory
    val mbs = ManagementFactory.getPlatformMBeanServer
    mbs.registerMBean(bean, objectName)
    lifecycle.addStopHook { () =>
      mbs.unregisterMBean(objectName)
      Future.successful(())
    }
  }
}