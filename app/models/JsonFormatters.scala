package models

import play.api.libs.json.Json

object JsonFormatters {

  implicit val commentFormat = Json.format[CommentEntity]

  implicit val rejectedFormat = Json.format[Rejected]

  implicit val acceptedFormat = Json.format[Accepted]

}
