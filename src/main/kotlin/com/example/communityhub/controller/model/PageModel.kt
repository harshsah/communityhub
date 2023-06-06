package com.example.communityhub.controller.model

class PageModel<T> (
	val pageNumber: Long,
	val pageSize: Int,
	val list: List<T>,
) {

	companion object {
		fun <R> empty() = PageModel<R>(
			pageNumber = 0,
			pageSize = 0,
			list = listOf()
		)
	}
}