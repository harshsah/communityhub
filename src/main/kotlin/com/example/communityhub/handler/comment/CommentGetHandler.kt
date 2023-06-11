package com.example.communityhub.handler.comment

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.CommentModel
import com.example.communityhub.controller.model.toModel
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommentDao
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CommentGetHandler(
	private val commentDao: CommentDao,
) : AbsHandler<CommentGetRequest, CommentGetResponse>(
	apiName = "comment get",
	errorResponseSupplier = { CommentGetResponse() }
){
	override suspend fun perform(request: CommentGetRequest): ResponseEntity<CommentGetResponse> {
		val comment = commentDao.findById(request.commentId)
			?: return ResponseEntity.noContent().build()
		return ResponseEntity.ok(CommentGetResponse(
			message = MessageConstant.OK,
			comment = comment.toModel()
		))
	}

}

data class CommentGetRequest(
	val commentId: String
)

data class CommentGetResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	val comment: CommentModel? = null
) : BaseResponse(message)
