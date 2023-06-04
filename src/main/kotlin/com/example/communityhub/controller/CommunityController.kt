package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.community.*
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/community")
class CommunityController (
	private val handlers: Handlers,
) {

	@PostMapping("/create/new")
	fun communityCreate(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: CommunityCreateRequestData,
	) = handlers.communityHandlers.communityCreateHandler
		.handle(CommunityCreateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData
		))

	@GetMapping("/{communityId}")
	fun communityGet(@PathVariable("communityId") communityId: String) = handlers
		.communityHandlers.communityGetHandler.handle(CommunityGetRequest(communityId))


	@PostMapping("/leave/community")
	fun communityJoin(
		servletRequest: HttpServletRequest,
		@RequestBody requestData: CommunityJoinRequestData,
	) = handlers.communityHandlers.communityJoinHandler
		.handle(CommunityJoinRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			data = requestData,
		))
}