package com.example.communityhub.handler.community

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.model.CommunityModel
import com.example.communityhub.controller.model.getCommunityModel
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.CommunityDao
import com.example.communityhub.handler.AbsHandler
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class CommunityGetHandler(
	val communityDao: CommunityDao,
) : AbsHandler<CommunityGetRequest, CommunityGetResponse>(
	apiName = "community get",
	errorResponseSupplier = Supplier { CommunityGetResponse() },
) {
	override suspend fun perform(request: CommunityGetRequest): ResponseEntity<CommunityGetResponse> {
		val communityId = request.communityId.lowercase()
		val community = communityDao.findById(communityId)
			?: return ResponseEntity.noContent().build()
		return ResponseEntity.ok(CommunityGetResponse(
			message = Message.OK,
			community = getCommunityModel(community)
		))
	}
}

data class CommunityGetRequest(
	val communityId: String,
)

data class CommunityGetResponse(
	override var message: String? = null,
	val community: CommunityModel? = null,
) : BaseResponse(message)

