package jmx

import com.tersesystems.jmxbuilder.DynamicBean
import javax.inject.{Inject, Singleton}
import javax.management.ObjectName
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class JMXServer @Inject()(lifecycle: ApplicationLifecycle) {

  def registerBean(objectName: ObjectName, bean: DynamicBean): Unit = {
    import java.lang.management.ManagementFactory
    val mbs = ManagementFactory.getPlatformMBeanServer
    mbs.registerMBean(bean, objectName)
    lifecycle.addStopHook { () =>
      mbs.unregisterMBean(objectName)
      Future.successful(())
    }
  }
}
