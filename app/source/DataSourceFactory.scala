package source

import commons.Constants

object DataSourceFactory {

  def apply(filterStrategy: Option[String]): ObjectionableDatasource = {

    val strategy = filterStrategy.getOrElse(Constants.SetFilterStrategy)

    strategy match {
      case Constants.SetFilterStrategy => SetDataSource
      case Constants.TrieFilterStrategy => TrieDataSource
      case Constants.LifoCacheFilterStrategy => LifoCacheDataSource
      case _ => SetDataSource
    }

  }
}
