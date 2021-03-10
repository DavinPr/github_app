package com.app.coremodule.domain.repository

import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun getAllFavorite(): Flow<Resource<List<User>>>
    fun insertFavorite(user: User)
    fun deleteFavorite(user: User)
}