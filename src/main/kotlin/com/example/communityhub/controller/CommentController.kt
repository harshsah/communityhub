package com.example.communityhub.controller

import com.example.communityhub.constant.PageConstant
import com.example.communityhub.dao.model.Comment
import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.comment.*
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import kotlin.math.min

@RestController
@RequestMapping("/comment")
class CommentController (
	private val handlers: Handlers,
) {

	@PostMapping("")
	fun commentCreate(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: CommentCreateRequestData,
	) = handlers.commentHandlers.commentCreateHandler
		.handle(CommentCreateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))

	@GetMapping("/list")
	fun commentList(
		@RequestParam(name = "postId", required = false) postId: String?,
		@RequestParam(name = "userId", required = false) userId: String?,
		@RequestParam(name = "pageNumber", required = false) pageNumber: Long?,
		@RequestParam(name = "pageSize", required = false) pageSize: Int?,
		@RequestParam(name = "sortProperty", required = false) sortProperty: String?,
		@RequestParam(name = "sortDirection", required = false) sortDirection: Int?,
	) = handlers.commentHandlers.commentListHandler
		.handle(CommentListRequest(
			postId = postId,
			userId = userId,
			pageNumber = pageNumber ?: 0,
			pageSize = min(PageConstant.COMMENT_MAX_PAGE_SIZE, pageSize ?: PageConstant.COMMENT_DEFAULT_PAGE_SIZE),
			sortProperty = sortProperty ?: Comment.DEFAULT_SORT_PROPERTY,
			sortDirection = sortDirection ?: -1,
		))

	@GetMapping("")
	fun commentGet(@RequestParam("id") commentId: String) = handlers.commentHandlers.commentGetHandler
		.handle(CommentGetRequest(commentId))

	@PostMapping("/delete")
	fun commentDelete(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: CommentDeleteRequestData,
	) = handlers.commentHandlers.commentDeleteHandler
		.handle(CommentDeleteRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))

}