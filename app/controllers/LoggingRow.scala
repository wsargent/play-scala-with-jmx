package controllers

import play.api.libs.json._
import java.time.Instant

case class LoggingRow(timestamp: Instant, name: String, level: String, message: String)

object LoggingRow {
  implicit val reads = Json.reads[LoggingRow]
}
