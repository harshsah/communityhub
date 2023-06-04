package com.example.communityhub.handler.community

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommunityDao
import com.example.communityhub.dao.impl.CommunityJoinDao
import com.example.communityhub.dao.model.CommunityJoin
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
class CommunityJoinHandler(
	private val communityJoinDao: CommunityJoinDao,
	private val communityDao: CommunityDao,
	private val jwtService: JwtService,
) : AbsHandler<CommunityJoinRequest, CommunityJoinResponse>(
	apiName = "community join",
	errorResponseSupplier = { CommunityJoinResponse() },
){
	override suspend fun perform(request: CommunityJoinRequest): ResponseEntity<CommunityJoinResponse> {
		val currentTimeMillis = System.currentTimeMillis()

		val userToken = jwtService.verifyToken(request.httpHeaders)
		val userId = userToken.id

		val communityId = request.data.communityId
			?: throw badRequestException(MessageConstant.COMMUNITY_ID_NOT_PRESENT)
		communityDao.findById(communityId)
			?: throw badRequestException(MessageConstant.COMMUNITY_ID_ALREADY_PRESENT)

		val communityJoinInDb = communityJoinDao.query()
			.`is`(CommunityJoin.USER_ID, userId)
			.`is`(CommunityJoin.COMMUNITY_ID, communityId)
			.findOne()
		if (request.data.join == false && communityJoinInDb != null) {
			communityJoinDao.deleteById(communityJoinInDb.id)
			return ResponseEntity.ok(CommunityJoinResponse(MessageConstant.OK))
		}

		if (communityJoinInDb == null) {
			val communityJoin = CommunityJoin(
				id = UUID.randomUUID().toString(),
				userId = userId,
				communityId = communityId,
				created = currentTimeMillis,
				updated = currentTimeMillis,
			)
			communityJoinDao.insert(communityJoin)
		}

		return ResponseEntity.ok(CommunityJoinResponse(MessageConstant.OK))
	}
}

data class CommunityJoinRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders,
	val data: CommunityJoinRequestData,
) : BaseRequest(httpHeaders)

data class CommunityJoinRequestData(
	val communityId: String?,
	val join: Boolean?,
)

data class CommunityJoinResponse(
	@LoggingGsonExclude
	override var message: String? = null
) : BaseResponse(message)