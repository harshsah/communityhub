package com.example.communityhub.handler.user

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.UserInfoModel
import com.example.communityhub.controller.model.getUserInfoModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UserInfoDao
import com.example.communityhub.exception.ServerException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.logging.LoggingMaskInterior
import com.example.communityhub.service.JwtService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserProfileHandler(
	val userInfoDao: UserInfoDao,
	val jwtService: JwtService,
) : AbsHandler<UserProfileRequest, UserProfileResponse>(
	apiName = "user profile get",
	errorResponseSupplier = { UserProfileResponse() }
) {

	override suspend fun perform(request: UserProfileRequest): ResponseEntity<UserProfileResponse> {
		val userId = request.userId.lowercase()
		val userInfo = userInfoDao.findById(userId)
			?: return ResponseEntity.noContent().build()
		val userToken = try {
			jwtService.verifyToken(request.httpHeaders)
		} catch (e: ServerException) {
			null // non-logged-in user/ expired user
		}
		val response = UserProfileResponse(
			message = MessageConstant.OK,
			user = getUserInfoModel(userInfo, userToken?.id == userInfo.id)
		)
		return ResponseEntity.ok(response)
	}
}

data class UserProfileRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders,
	val userId: String,
) : BaseRequest(httpHeaders)

open class UserProfileResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	@LoggingMaskInterior(classes = [UserInfoModel::class])
	val user: UserInfoModel? = null
) : BaseResponse(message)



