package com.app.coremodule.data.remote

import com.app.coremodule.data.remote.response.UserResponse
import com.google.gson.Gson

class JsonHelper(private val jsonString: String, private val gson: Gson) : JsonService {
    override suspend fun getUser(): UserResponse =
        gson.fromJson(jsonString, UserResponse::class.java)
}