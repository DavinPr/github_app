package com.app.coremodule.domain.usecase

import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getAllUser(): Flow<Resource<List<User>>>
}