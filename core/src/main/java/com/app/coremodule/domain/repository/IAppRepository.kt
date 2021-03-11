package com.app.coremodule.domain.repository

import com.app.coremodule.data.Resource
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.SearchItem
import com.app.coremodule.data.remote.response.UserFollowResponse
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getSearchUser(username: String): Flow<Resource<List<SearchItem>>>
    fun getDetailUser(username: String): Flow<Resource<DetailResponse>>
    fun getUserFollowing(username: String): Flow<Resource<List<UserFollowResponse>>>
    fun getUserFollowers(username: String): Flow<Resource<List<UserFollowResponse>>>
    fun getAllFavorite(): Flow<Resource<List<User>>>
    fun insertFavorite(user: User)
    fun deleteFavorite(user: User)
}