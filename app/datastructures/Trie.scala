
//Taken from https://gist.github.com/archie/7014770

package datastructures

case class Node(value: String,
                var children: Map[Char, Node] = Map(),
                var isValidWord: Boolean = false)

class Trie {
  private val root = Node("")

  def addWord(word: String) = {
    val chars = word.toCharArray
    var curnode = root
    for (ch <- chars) {
      if (!curnode.children.contains(ch))
        curnode.children += (ch -> Node(curnode.value + ch))

      curnode = curnode.children(ch)
    }
    curnode.isValidWord = true
  }

  def contains(word: String, isWord: Boolean = true): Boolean = getNode(word) match {
    case Some(n: Node) => ((n.isValidWord && isWord) || (!isWord))
    case None => false
  }

  def getNode(arg: String): Option[Node] = {
    def nodeFinder(node: Node, chpos: Int): Option[Node] =
      node.children.get(arg(chpos)) match {
        case Some(n) if chpos == arg.length - 1 => Some(n)
        case Some(n) => nodeFinder(n, chpos + 1)
        case None => None
      }

    nodeFinder(root, 0)
  }
}

object Trie {
  def apply(words: Seq[String]): Trie = {
    val t = new Trie
    for (word <- words)
      t.addWord(word)
    t
  }
}
