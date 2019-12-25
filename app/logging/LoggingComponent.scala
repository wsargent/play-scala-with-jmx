package logging

import ch.qos.logback.classic
import com.tersesystems.jmxbuilder.{AttributeInfo, DescriptorSupport, DynamicBean}
import com.tersesystems.logback.classic.ChangeLogLevel
import org.slf4j.Logger

import scala.jdk.CollectionConverters._

trait LoggingComponent {
  def setLoggingLevel(level: String): Unit
  def getLoggingLevel: String
}

trait LogbackLoggingComponent extends LoggingComponent {
  protected def logbackLogger: Logger

  override def getLoggingLevel: String = {
    val logger = logbackLogger.asInstanceOf[classic.Logger]
    logger.getEffectiveLevel.toString
  }

  override def setLoggingLevel(level: String): Unit = {
    new ChangeLogLevel().changeLogLevel(logbackLogger, level)
  }
}

object LoggingComponent {
  def jmxBuilder(lc: LoggingComponent): DynamicBean.Builder = {
    val descriptor = DescriptorSupport.builder()
      .withDisplayName("level")
      .withLegalValues(Set("OFF", "ERROR", "WARN", "INFO", "DEBUG", "TRACE").asJava)
      .build()

    val attributeInfo = AttributeInfo.builder(classOf[String])
      .withName("loggingLevel")
      .withBeanProperty(lc, "loggingLevel")
      .withDescriptor(descriptor)
      .build()
    DynamicBean.builder.withAttribute(attributeInfo)
  }
}
