package controllers

import datastructures.Trie
import org.scalatestplus.play._
import play.api.test._

class TrieSpec extends PlaySpec with FutureAwaits with DefaultAwaitTimeout {

  "Trie" should {

    "add new word " in {
      val trie = Trie(Seq())

      val newWord = "hell"
      trie.contains(newWord) mustBe false

      trie.addWord(newWord)
      trie.contains(newWord) mustBe true

    }

    "return true for already added  words" in {
      val trie = Trie(Seq("hell", "crap"))

      trie.contains("hell") mustBe true
      trie.contains("crap") mustBe true
    }
  }
}
