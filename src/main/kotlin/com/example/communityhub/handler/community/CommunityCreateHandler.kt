package com.example.communityhub.handler.community

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.CommunityModel
import com.example.communityhub.controller.model.getCommunityModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommunityDao
import com.example.communityhub.dao.model.Community
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.service.JwtService
import com.example.communityhub.utils.StringUtils.isAlphaNumeric
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CommunityCreateHandler(
	val communityDao: CommunityDao,
	val jwtService: JwtService,
) : AbsHandler<CommunityCreateRequest, CommunityCreateResponse>(
	apiName = "community create",
	errorResponseSupplier = { CommunityCreateResponse() },
) {
	override suspend fun perform(request: CommunityCreateRequest): ResponseEntity<CommunityCreateResponse> {
		val currentTimeMillis = System.currentTimeMillis()

		val userToken = jwtService.verifyToken(request.httpHeaders)
		val userId = userToken.id

		if (request.data.id.isNullOrEmpty() || !request.data.id.isAlphaNumeric()) {
			throw badRequestException(MessageConstant.COMMUNITY_ID_NOT_PRESENT)
		}

		val communityId: String = request.data.id.lowercase()
		communityDao.findById(communityId) ?: throw badRequestException(MessageConstant.COMMUNITY_ID_ALREADY_PRESENT)

		val community = Community(
			id = communityId,
			name = request.data.name ?: "",
			iconImage = request.data.iconImage,
			description = request.data.description ?: "",
			tabs = request.data.tabs,
			mods = listOf(userId),
			createdBy = userId,
			created = currentTimeMillis,
			updated = currentTimeMillis,
		)
		communityDao.insert(community)

		return ResponseEntity.ok(CommunityCreateResponse(
			message = MessageConstant.CREATED,
			community = getCommunityModel(community),
			))
	}
}

data class CommunityCreateRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders,
	val data: CommunityCreateRequestData,
): BaseRequest(httpHeaders)

data class CommunityCreateRequestData(
	val id: String? = null,
	val name: String? = null,
	val iconImage: String? = null,
	val description: String? = null,
	val tabs: Map<String, String> = HashMap(),
)

data class CommunityCreateResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	val community: CommunityModel? = null,
): BaseResponse(message)