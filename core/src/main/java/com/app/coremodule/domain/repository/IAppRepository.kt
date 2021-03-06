package com.app.coremodule.domain.repository

import com.app.coremodule.data.Resource
import com.app.coremodule.data.remote.ApiResponse
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getAllUsers(): Flow<Resource<List<User>>>
}