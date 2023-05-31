package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.comment.CommentCreateRequest
import com.example.communityhub.handler.comment.CommentCreateRequestData
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentController (
	private val handlers: Handlers,
) {

	@PostMapping("/create/new")
	fun commentCreate(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: CommentCreateRequestData,
	) = handlers.commentHandlers.commentCreateHandler
		.handle(CommentCreateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))
}