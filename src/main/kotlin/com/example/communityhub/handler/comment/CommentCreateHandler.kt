package com.example.communityhub.handler.comment

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommentDao
import com.example.communityhub.dao.impl.PostDao
import com.example.communityhub.dao.model.Comment
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.service.JwtService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentCreateHandler(
	private val jwtService: JwtService,
	private val postDao: PostDao,
	private val commentDao: CommentDao,
) : AbsHandler<CommentCreateRequest, CommentCreateResponse>(
	apiName = "comment create",
	errorResponseSupplier = { CommentCreateResponse() }
) {
	override suspend fun perform(request: CommentCreateRequest): ResponseEntity<CommentCreateResponse> {
		val currentTimeMillis = System.currentTimeMillis()
		val userId = jwtService.verifyToken(request.httpHeaders).id
		val content = request.data.content?.takeIf { it.isNotEmpty() }
			?: throw badRequestException(Message.INVALID_REQUEST)
		val postId = request.data.postId?.takeIf { it.isNotEmpty() && postDao.existsById(it)}
			?: throw badRequestException(Message.POST_NOT_FOUND)
		val parentId = request.data.parentId?.let {
			if (!commentDao.existsById(it)) {
				throw badRequestException(Message.PARENT_COMMENT_NOT_FOUND)
			}
			it
		}

		val comment = Comment(
			id = UUID.randomUUID().toString(),
			content = content,
			postId = postId,
			userId = userId,
			parentId = parentId,
			created = currentTimeMillis,
			updated = currentTimeMillis,
		)
		commentDao.insert(comment)
		return ResponseEntity.ok(CommentCreateResponse(Message.OK))
	}
}

data class CommentCreateRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders = HttpHeaders(),
	val data: CommentCreateRequestData,
) : BaseRequest(httpHeaders)

data class CommentCreateRequestData(
	val content: String?,
	val postId: String?,
	val parentId: String?,
)

data class CommentCreateResponse(
	override var message: String? = null
) : BaseResponse(message)
