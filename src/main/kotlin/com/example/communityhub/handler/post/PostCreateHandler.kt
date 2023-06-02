package com.example.communityhub.handler.post

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.PostModel
import com.example.communityhub.controller.model.getPostModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommunityDao
import com.example.communityhub.dao.impl.PostDao
import com.example.communityhub.dao.model.Post
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
class PostCreateHandler(
	private val jwtService: JwtService,
	private val postDao: PostDao,
	private val communityDao: CommunityDao,
) : AbsHandler<PostCreateRequest, PostCreateResponse>(
	apiName = "post create",
	errorResponseSupplier = { PostCreateResponse() }
) {
	override suspend fun perform(request: PostCreateRequest): ResponseEntity<PostCreateResponse> {
		val currentTimeMillis = System.currentTimeMillis()
		val userToken = jwtService.verifyToken(request.httpHeaders)
		val userId = userToken.id;
		val communityId = request.data.communityId
		if (communityId.isEmpty() || !communityDao.existsById(communityId)) {
			throw badRequestException(MessageConstant.COMMUNITY_ID_NOT_PRESENT)
		}
		val post = Post(
			id = UUID.randomUUID().toString(),
			title = request.data.title,
			content = request.data.content,
			userId = userId,
			communityId = communityId,
			created = currentTimeMillis,
			updated = currentTimeMillis,
		)
		postDao.insert(post)
		return ResponseEntity.ok(PostCreateResponse(
			message = MessageConstant.CREATED,
			post = getPostModel(post),
		))
	}
}

data class PostCreateRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders,
	val data: PostCreateRequestData,
) : BaseRequest(httpHeaders)

data class PostCreateRequestData(
	val title: String = "",
	val content: String = "",
	val communityId: String = "",
)

data class PostCreateResponse(
	override var message: String? = null,
	val post: PostModel? = null,
) : BaseResponse(message)
