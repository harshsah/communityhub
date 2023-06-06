package com.example.communityhub.utils

object PageUtils {

	fun getSkipLimitPair(pageNumber: Long, pageSize: Int) = Pair(pageNumber * pageSize, pageSize)
}