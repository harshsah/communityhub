package com.example.communityhub.controller

import com.example.communityhub.constant.PageConstant
import com.example.communityhub.dao.model.Post
import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.post.PostCreateRequest
import com.example.communityhub.handler.post.PostCreateRequestData
import com.example.communityhub.handler.post.PostListRequest
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import kotlin.math.min

@RestController
@RequestMapping("/post")
class PostController (
	private val handlers: Handlers,
) {

	@PostMapping("")
	fun postCreate(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: PostCreateRequestData
	) = handlers.postHandlers.postCreateHandler
		.handle(PostCreateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))

	@GetMapping("/list")
	fun commentList(
		@RequestParam(name = "communityId", required = false) communityId: String?,
		@RequestParam(name = "userId", required = false) userId: String?,
		@RequestParam(name = "pageNumber", required = false) pageNumber: Long?,
		@RequestParam(name = "pageNumber", required = false) pageSize: Int?,
		@RequestParam(name = "sortProperty", required = false) sortProperty: String?,
		@RequestParam(name = "sortDirection", required = false) sortDirection: Int?,
	) = handlers.postHandlers.postListHandler
		.handle(PostListRequest(
			communityId = communityId,
			userId = userId,
			pageNumber = pageNumber ?: 0,
			pageSize = min(PageConstant.POST_MAX_PAGE_SIZE, pageSize ?: PageConstant.POST_DEFAULT_PAGE_SIZE),
			sortProperty = sortProperty ?: Post.DEFAULT_SORT_PROPERTY,
			sortDirection = sortDirection ?: -1,
		))
}