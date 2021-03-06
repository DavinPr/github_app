package com.app.coremodule.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("users")
	val users: List<UsersItem>? = null
)

data class UsersItem(

	@field:SerializedName("follower")
	val follower: Int? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("repository")
	val repository: Int? = null,

	@field:SerializedName("username")
	val username: String
)
