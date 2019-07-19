package source

import datastructures.Trie

import scala.concurrent.Future

object TrieDataSource extends ObjectionableDatasource {

  val trie = Trie(DataLoader.data)

  override def contains(word: String): Future[Boolean] = Future.successful(trie.contains(word))

  override def add(word: String): Future[Unit] = Future.successful(trie.addWord(word))
}
