package com.app.coremodule.domain.repository

import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getSearchUser(username: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<Detail>>
    fun getUserFollowing(username: String): Flow<Resource<List<User>>>
    fun getUserFollowers(username: String): Flow<Resource<List<User>>>

    fun getAllFavorite(): Flow<Resource<List<User>>>
    fun insertFavorite(user: User)
    fun deleteFavorite(user: User)

    fun putFragmentTag(tag: String)
    fun getFragmentTag(): String?
}