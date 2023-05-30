package com.example.communityhub.controller.model

import com.example.communityhub.dao.model.Community

data class CommunityModel(
	val id: String,
	val name: String,
	val iconImage: String?,
	val description: String,
	val tabs: Map<String, String>,
	val mods: List<String>,
	val createdBy: String,
	val created: Long,
)

fun getCommunityModel(community: Community) = CommunityModel(
	id = community.id,
	name = community.name,
	iconImage = community.iconImage,
	description = community.description,
	tabs = community.tabs,
	mods = community.mods,
	createdBy = community.createdBy,
	created = community.created,
)