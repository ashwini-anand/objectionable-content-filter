package models

case class CommentEntity(comment: String)

case class Rejected(message: String, detectedObjectionalWords: Seq[String])

case class Accepted(message: String = "Comment successfully posted")