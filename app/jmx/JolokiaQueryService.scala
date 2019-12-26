package jmx

import java.time.Instant
import java.time.format.DateTimeFormatter

import controllers.LoggingRow
import org.jolokia.client.J4pClient
import org.jolokia.client.request._
import javax.inject.Singleton
import org.json.simple.JSONObject
import play.api.libs.json._

/**
 * Queries Jolokia to render the events in the cyclic buffer as a list.
 */
@Singleton
class JolokiaQueryService {

  def loggingEvents: Seq[LoggingRow] = {
    val client = new J4pClient("http://localhost:8778/jolokia")
    val request = new J4pReadRequest("play:type=CyclicBuffer,name=logging.LoggingEventsBean","events")
    val response: J4pReadResponse = client.execute(request)
    val result: JSONObject = response.getValue()
    val unsortedList = Json.parse(result.toJSONString).as[Map[String, JsValue]].toIndexedSeq
    unsortedList.sortBy { case (isoDate, _) =>
      Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(isoDate))
    }.map { case (_, v) => v.as[LoggingRow] }
  }

}
