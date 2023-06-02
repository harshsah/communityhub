package com.example.communityhub.handler.user

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.model.UserInfoModel
import com.example.communityhub.controller.model.getUserInfoModel
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UserInfoDao
import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.dao.model.UserInfoData
import com.example.communityhub.exception.internalServerErrorException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.logging.LoggingMaskInterior
import com.example.communityhub.service.JwtService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserProfileUpdateHandler(
	val userInfoDao: UserInfoDao,
	val jwtService: JwtService,
) : AbsHandler<UserProfileUpdateRequest, UserProfileUpdateResponse>(
	apiName = "user profile update",
	errorResponseSupplier = { UserProfileUpdateResponse() },
) {
	override suspend fun perform(request: UserProfileUpdateRequest): ResponseEntity<UserProfileUpdateResponse> {
		val userToken = jwtService.verifyToken(request.httpHeaders)
		val id = userToken.id

		val updateMap = mutableMapOf<String, Any>()

		if (request.userInfoData != null) {
			val name = request.userInfoData.name
			val iconImage = request.userInfoData.iconImage
			if (!name.isNullOrEmpty()) {
				updateMap[UserInfo.USER_INFO_DATA + "." + UserInfoData.NAME] = name
			}
			if (!iconImage.isNullOrEmpty()) {
				updateMap[UserInfo.USER_INFO_DATA + "." + UserInfoData.ICON_IMAGE] = iconImage
			}
		}

		userInfoDao.query()
			.`is`(UserInfo.ID, id)
			.updateOne(updateMap)
		val userInfo = userInfoDao.findById(id)
			?: throw internalServerErrorException(message = "Data Not Found $id")

		return ResponseEntity.ok(UserProfileUpdateResponse(
			message = MessageConstant.OK,
			user = getUserInfoModel(userInfo, true)
		))
	}
}

data class UserProfileUpdateRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders = HttpHeaders(),
	val userInfoData: UserProfileUpdateRequestData?
) : BaseRequest(httpHeaders)

data class UserProfileUpdateRequestData(
	val name: String?,
	val iconImage: String?,
)

data class UserProfileUpdateResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	@LoggingMaskInterior(classes = [UserInfoModel::class])
	val user: UserInfoModel? = null
) : BaseResponse(message)