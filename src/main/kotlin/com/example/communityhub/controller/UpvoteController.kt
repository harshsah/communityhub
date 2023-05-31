package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.upvote.UpvoteRequest
import com.example.communityhub.handler.upvote.UpvoteRequestData
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/upvote")
class UpvoteController (
	private val handlers: Handlers,
) {

	@PostMapping("")
	fun upvote(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: UpvoteRequestData,
	) = handlers.upvoteHandlers.upvoteHandler
		.handle(UpvoteRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData
		))
}