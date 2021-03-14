package com.app.coremodule.domain.usecase

import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getSearchUser(username: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<Detail>>
    fun getUserFollowers(username: String) : Flow<Resource<List<User>>>
    fun getUserFollowing(username: String) : Flow<Resource<List<User>>>
    fun getAllFavorite(): Flow<Resource<List<User>>>
    fun insertFavorite(user: User)
    fun deleteFavorite(user: User)
    fun putFragmentTag(tag: String)
    fun getFragmentTag(): String?
    fun putDetailFragmentTag(tag: String)
    fun getDetailFragmentTag(): String?
}