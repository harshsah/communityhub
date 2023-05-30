package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.post.PostCreateRequest
import com.example.communityhub.handler.post.PostCreateRequestData
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController (
	private val handlers: Handlers,
) {

	@PostMapping("/new/create")
	fun postCreate(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: PostCreateRequestData
	) = handlers.postHandlers.postCreateHandler
		.handle(PostCreateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))
}