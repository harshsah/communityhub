package com.example.communityhub.controller.model

import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.logging.LoggingMask
import com.example.communityhub.logging.LoggingMaskInterior

data class UserInfoModel(
	val id: String,
	val userInfoData: UserInfoDataModel,
	@LoggingMaskInterior(classes = [UserInfoContactModel::class])
	val userInfoContact: UserInfoContactModel? = null,
)

open class UserInfoDataModel(
	val name: String?,
	val iconImage: String?,
)

open class UserInfoContactModel(
	@LoggingMask
	val phone: String?,
	@LoggingMask
	val email: String?,
)

fun getUserInfoModel(userInfo: UserInfo, showPrivate: Boolean) = if (showPrivate) {
	UserInfoModel(
		id = userInfo.id,
		userInfoData = UserInfoDataModel(
			name = userInfo.userInfoData?.name,
			iconImage = userInfo.userInfoData?.iconImage,
		),
		userInfoContact = UserInfoContactModel(
			phone = userInfo.userInfoContact?.phone,
			email = userInfo.userInfoContact?.email,
		)
	)
} else {
	UserInfoModel(
		id = userInfo.id,
		userInfoData = UserInfoDataModel(
			name = userInfo.userInfoData?.name,
			iconImage = userInfo.userInfoData?.iconImage,
		)
	)
}