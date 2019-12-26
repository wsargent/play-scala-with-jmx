import ch.qos.logback.classic.LoggerContext
import com.google.inject.AbstractModule
import jmx.JMXServer
import logging.LoggingEventsBean
import org.slf4j.LoggerFactory

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[LoggerContext]).toInstance(LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext])
    bind(classOf[JMXServer]).asEagerSingleton()
    bind(classOf[LoggingEventsBean]).asEagerSingleton()
  }
}
