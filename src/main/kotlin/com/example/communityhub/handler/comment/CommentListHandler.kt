package com.example.communityhub.handler.comment

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.CommentModel
import com.example.communityhub.controller.model.PageModel
import com.example.communityhub.controller.model.getCommentModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommentDao
import com.example.communityhub.dao.model.Comment
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.utils.PageUtils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CommentListHandler(
	private val commentDao: CommentDao,
) : AbsHandler<CommentListRequest, CommentListResponse>(
	apiName = "comment list",
	errorResponseSupplier = { CommentListResponse() }
) {
	override suspend fun perform(request: CommentListRequest): ResponseEntity<CommentListResponse> {
		val (skip, limit) = PageUtils.getSkipLimitPair(request.pageNumber, request.pageSize)
		val commentList = if (request.postId != null) {
			commentDao.query()
				.`is`(Comment.POST_ID, request.postId)
				.sort(getPostSortProperty(request.sortProperty), request.sortDirection)
				.skip(skip)
				.limit(limit)
				.findAll()
		} else if (request.userId != null) {
			commentDao.query()
				.`is`(Comment.USER_ID, request.userId)
				.sort(getPostSortProperty(request.sortProperty), request.sortDirection)
				.skip(skip)
				.limit(limit)
				.findAll()
		} else {
			listOf()
		}
		val responseList = commentList.map { getCommentModel(it) }
		return ResponseEntity.ok(CommentListResponse(
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
	if (property in Comment.VALID_SORT_PROPERTIES) {
		property
	} else {
		Comment.DEFAULT_SORT_PROPERTY
	}

data class CommentListRequest(
	val postId: String?,
	val userId: String?,
	val pageNumber: Long,
	val pageSize: Int,
	val sortProperty: String,
	val sortDirection: Int,
) : BaseRequest()

data class CommentListResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	val page: PageModel<CommentModel>? = null,
) : BaseResponse(message)
