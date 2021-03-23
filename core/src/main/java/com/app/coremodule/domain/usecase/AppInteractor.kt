package com.app.coremodule.domain.usecase

import com.app.coremodule.data.AppRepository
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.Recent
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

class AppInteractor(private val repository: AppRepository) : AppUseCase {
    override fun getSearchUser(username: String): Flow<Resource<List<User>>> =
        repository.getSearchUser(username)

    override fun getDetailUser(username: String): Flow<Resource<Detail>> =
        repository.getDetailUser(username)

    override fun getUserFollowers(username: String): Flow<Resource<List<User>>> =
        repository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> =
        repository.getUserFollowing(username)

    override fun getAllFavorite(): Flow<Resource<List<User>>> = repository.getAllFavorite()

    override fun insertFavorite(detail: Detail) = repository.insertFavorite(detail)

    override fun deleteFavorite(detail: Detail) = repository.deleteFavorite(detail)

    override fun getAllRecent(): Flow<List<Recent>> = repository.getAllRecent()

    override fun insertRecent(detail: Detail) = repository.insertRecent(detail)

    override fun getLocale(): String? = repository.getLocale()

}