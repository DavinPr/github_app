package com.app.coremodule.data.remote

import com.app.coremodule.data.remote.response.UserResponse

interface JsonService {
    suspend fun getUser(): UserResponse
}