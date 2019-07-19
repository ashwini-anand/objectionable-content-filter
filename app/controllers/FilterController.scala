package controllers

import filters.CommentsFilter
import javax.inject._
import models.{CommentEntity, Rejected}
import play.api._
import play.api.mvc._
import models.JsonFormatters._
import play.api.libs.json.Json
import source.DataSourceFactory

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class FilterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Logging {

  def postComment(filterStrategy: Option[String]) = Action.async(parse.json) { request =>
    logger.info("Received request to post comment")
    request.body
      .validate[CommentEntity]
      .map { commentEntity =>

        val dataSource = DataSourceFactory(filterStrategy)

        val commentsFilter = new CommentsFilter(dataSource)

        commentsFilter.filter(commentEntity.comment).map { response =>
          constructResult(response)
        }
          .recoverWith {
            case th: Throwable => val msg = "Failed to post comment"
              Future.failed(new Exception(msg, th))
          }
      }
      .getOrElse {
        logger.warn("Not able to post comment. Failed to map request to Comment entity")
        Future.successful(BadRequest)
      }
  }

  private def constructResult(res: Seq[String]) = {
    res match {
      case Nil => Created(Json.toJson(models.Accepted()))
      case _ => {
        val rejected = Rejected("Comment rejected. Comment contains objectionable words.", res)
        InternalServerError(Json.toJson(rejected))
      }
    }
  }
}
