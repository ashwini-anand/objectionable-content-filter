package source

import scala.concurrent.Future

object LifoCacheDataSource extends ObjectionableDatasource {

  override def contains(word: String): Future[Boolean] = ???

  override def add(word: String): Future[Unit] = ???
}
