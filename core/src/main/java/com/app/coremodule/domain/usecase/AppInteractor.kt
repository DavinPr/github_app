package com.app.coremodule.domain.usecase

import com.app.coremodule.data.AppRepository
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

class AppInteractor(private val repository: AppRepository) : AppUseCase {
    override fun getAllUser(): Flow<Resource<List<User>>> = repository.getAllUsers()
}