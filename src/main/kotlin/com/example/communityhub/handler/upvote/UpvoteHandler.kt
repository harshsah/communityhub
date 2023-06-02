package com.example.communityhub.handler.upvote

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UpvoteDao
import com.example.communityhub.dao.model.Upvote
import com.example.communityhub.dao.model.UpvoteEntityType
import com.example.communityhub.dao.model.UpvoteType
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.service.JwtService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UpvoteHandler (
	private val jwtService: JwtService,
	private val upvoteDao: UpvoteDao,
) : AbsHandler<UpvoteRequest, UpvoteResponse>(
	apiName = "post upvote",
	errorResponseSupplier = { UpvoteResponse() }
) {
	override suspend fun perform(request: UpvoteRequest): ResponseEntity<UpvoteResponse> {

		val currentTimeMillis = System.currentTimeMillis()
		val userId = jwtService.verifyToken(request.httpHeaders).id
		val (entityType, entityId) = getEntityPair(request)

		val upvoteType = request.data.type?.takeIf { it.isNotEmpty() }
			?.let { UpvoteType.getFromName(it) }
			?: throw badRequestException(MessageConstant.INVALID_REQUEST)

		val upvoteInDb = upvoteDao.query()
			.`is`(Upvote.TYPE, entityType)
			.`is`(Upvote.ENTITY_ID, entityId)
			.`is`(Upvote.USER_ID, userId)
			.isNot(Upvote.TYPE, upvoteType)
			.findOne()

		if (upvoteInDb != null) {
			upvoteDao.query()
				.`is`(Upvote.ID, upvoteInDb.id)
				.updateOne(mapOf(Upvote.TYPE to upvoteType))
		} else {
			val upvote = Upvote(
				id = UUID.randomUUID().toString(),
				entityType = entityType,
				entityId = entityId,
				userId = userId,
				type = upvoteType,
				created = currentTimeMillis,
				updated = currentTimeMillis,
			)
			upvoteDao.insert(upvote)
		}
		return ResponseEntity.ok(UpvoteResponse(MessageConstant.OK))
	}

	private fun getEntityPair(request: UpvoteRequest) : Pair<UpvoteEntityType, String> {
		request.data.postId?.takeIf { it.isNotEmpty() }
			?.let { return Pair(UpvoteEntityType.POST, it) }
		request.data.commentId?.takeIf { it.isNotEmpty() }
			?.let { return Pair(UpvoteEntityType.COMMENT, it) }
		throw badRequestException(MessageConstant.POST_NOT_FOUND)
	}
}


data class UpvoteRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders = HttpHeaders(),
	val data: UpvoteRequestData
) : BaseRequest(httpHeaders)

data class UpvoteRequestData(
	val postId: String?,
	val commentId: String?,
	val type: String?,
)

data class UpvoteResponse(
	@LoggingGsonExclude
	override var message: String? = null,
) : BaseResponse(message)