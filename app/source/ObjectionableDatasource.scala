package source

import scala.concurrent.Future

trait ObjectionableDatasource {
  def contains(word: String): Future[Boolean]
  def add(word: String): Future[Unit]
}
