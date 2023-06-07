package com.example.communityhub.handler.post

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.PageModel
import com.example.communityhub.controller.model.PostModel
import com.example.communityhub.controller.model.getPostModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.PostDao
import com.example.communityhub.dao.model.Post
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.utils.PageUtils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class PostListHandler(
	private val postDao: PostDao,
) : AbsHandler<PostListRequest, PostListResponse>(
	apiName = "post list",
	errorResponseSupplier = { PostListResponse() }
){
	override suspend fun perform(request: PostListRequest): ResponseEntity<PostListResponse> {
		val (skip, limit) = PageUtils.getSkipLimitPair(request.pageNumber, request.pageSize)
		val postList = if (request.communityId != null) {
			postDao.query()
				.`is`(Post.COMMUNITY_ID, request.communityId)
				.sort(getPostSortProperty(request.sortProperty), request.sortDirection)
				.skip(skip)
				.limit(limit)
				.findAll()
		} else if (request.userId != null) {
			postDao.query()
				.`is`(Post.USER_ID, request.userId)
				.sort(getPostSortProperty(request.sortProperty), request.sortDirection)
				.skip(skip)
				.limit(limit)
				.findAll()
		} else {
			listOf()
		}
		val responseList = postList.map { getPostModel(it) }
		return ResponseEntity.ok(PostListResponse(
			message = MessageConstant.OK,
			page = PageModel(
				pageNumber = request.pageNumber,
				pageSize = request.pageSize,
				list = responseList,
			)
		))
	}
}

private fun getPostSortProperty(property: String) =
	if (property in Post.VALID_SORT_PROPERTIES) {
		property
	} else {
		Post.DEFAULT_SORT_PROPERTY
	}

data class PostListRequest(
	val communityId: String?,
	val userId: String?,
	val pageNumber: Long,
	val pageSize: Int,
	val sortProperty: String,
	val sortDirection: Int,
) : BaseRequest()

data class PostListResponse(
	override var message: String? = null,
	val page: PageModel<PostModel>? = null,
) : BaseResponse(message)
