package source

import scala.concurrent.Future

object SetDataSource extends ObjectionableDatasource {

  val objectionableWordsSet = DataLoader.data.toSet

  override def contains(word: String): Future[Boolean] = Future.successful(objectionableWordsSet.contains(word))

  override def add(word: String): Future[Unit] = ???

}
