package filters

import com.typesafe.config.ConfigFactory
import source.ObjectionableDatasource

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CommentsFilter(dataSource: ObjectionableDatasource) {

  val config = ConfigFactory.load()
  val wordSeparator = config.getString("word.separator")

  def filter(comment: String): Future[Seq[String]] = {
    val words = getWords(comment)
    Future.sequence(words.map(dataSource.contains(_)))
      .map { boolSeq => (words zip boolSeq).filter(_._2).map(_._1) }
  }

  private def getWords(comment: String) = {
    val lowerCaseComment = comment.toLowerCase()
    lowerCaseComment.split(wordSeparator).toSeq
  }
}
