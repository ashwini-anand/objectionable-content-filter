package controllers

import filters.CommentsFilter
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.test._
import source.ObjectionableDatasource

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CommentsFilterSpec extends PlaySpec with MockitoSugar with FutureAwaits with DefaultAwaitTimeout {

  "Comments Filter" should {

    "get list of objectionable words in a comment" in {
      val datasource = mock[ObjectionableDatasource]
      val comment = "Hell is crap"

      when(datasource.contains("hell")).thenReturn(Future(true))
      when(datasource.contains("is")).thenReturn(Future(false))
      when(datasource.contains("crap")).thenReturn(Future(true))

      val commentFilter = new CommentsFilter(datasource)

      val detectedObjectionableWords = await(commentFilter.filter(comment))
      detectedObjectionableWords.size mustBe 2
      detectedObjectionableWords(0).toLowerCase() mustBe "hell"
      detectedObjectionableWords(1).toLowerCase() mustBe "crap"


    }

    "get empty list if no objectionable words found in comment" in {
      val datasource = mock[ObjectionableDatasource]
      val comment = "Life is good"

      when(datasource.contains("life")).thenReturn(Future(false))
      when(datasource.contains("is")).thenReturn(Future(false))
      when(datasource.contains("good")).thenReturn(Future(false))

      val commentFilter = new CommentsFilter(datasource)

      val detectedObjectionableWords = await(commentFilter.filter(comment))
      detectedObjectionableWords.size mustBe 0
    }
  }
}
