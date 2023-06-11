package com.example.communityhub.handler.comment

import com.example.communityhub.constant.GlobalConstant
import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommentDao
import com.example.communityhub.dao.impl.CommunityDao
import com.example.communityhub.dao.model.Comment
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.service.JwtService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CommentDeleteHandler(
	private val jwtService: JwtService,
	private val commentDao: CommentDao,
	private val communityDao: CommunityDao,
) : AbsHandler<CommentDeleteRequest, CommentDeleteResponse> (
	apiName = "comment delete",
	errorResponseSupplier = { CommentDeleteResponse() }
){
	override suspend fun perform(request: CommentDeleteRequest): ResponseEntity<CommentDeleteResponse> {
		val currentTimeMillis = System.currentTimeMillis();
		val userToken = jwtService.verifyToken(request.httpHeaders)
		val userId = userToken.id
		val commentId = request.data.commentId ?: throw badRequestException(MessageConstant.COMMENT_NOT_FOUND)
		val comment = commentDao.findById(commentId) ?: throw badRequestException(MessageConstant.COMMENT_NOT_FOUND)
		val updateMap: Map<String, Any> = when {
			request.data.isModDelete
					&& communityDao.findById(comment.communityId)?.mods?.contains(userId) ?: false -> mapOf(
				Comment.USER_ID to GlobalConstant.USER_ID_DELETED_BY_MOD,
				Comment.CONTENT to GlobalConstant.CONTENT_DELETED,
				Comment.UPDATED to currentTimeMillis,
			)
			userId == comment.userId -> mapOf(
				Comment.USER_ID to GlobalConstant.USER_ID_DELETED_BY_USER,
				Comment.CONTENT to GlobalConstant.CONTENT_DELETED,
				Comment.UPDATED to currentTimeMillis,
			)
			else -> throw badRequestException(MessageConstant.INVALID_REQUEST);
		}
		commentDao.query()
			.`is`(Comment.ID, comment.id)
			.updateOne(updateMap)
		return ResponseEntity.ok(CommentDeleteResponse(MessageConstant.DELETED))
	}
}


data class CommentDeleteRequest(
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders,
	val data: CommentDeleteRequestData,
) : BaseRequest(httpHeaders)

data class CommentDeleteRequestData(
	val commentId: String?,
	@JsonProperty("isModDelete")
	val isModDelete: Boolean = false,
)

data class CommentDeleteResponse(
	@LoggingGsonExclude
	override var message: String? = null
) : BaseResponse(message)