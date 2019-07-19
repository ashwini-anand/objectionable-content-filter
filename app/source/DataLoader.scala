package source

import com.typesafe.config.ConfigFactory
import commons.{Constants, ObjectionableWords}

import scala.io.Source

object DataLoader {

  val config = ConfigFactory.load()
  val loadStrategy: String = config.getString("loadData.strategy")
  val objectionableWordsFilePath: String = config.getString("objectionablewords.filepath")

  val data = loadStrategy match {
    case Constants.InMemDataLoadStrategy => ObjectionableWords.objectionableWordsEn
    case _ => loadDataFromExternalMem
  }

  def loadDataFromExternalMem(): Seq[String] = Source.fromFile(objectionableWordsFilePath).mkString.split(",").toSeq

}
