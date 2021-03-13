package com.app.coremodule.data.remote

import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.data.remote.network.ApiService
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getSearchUser(username: String): Flow<ApiResponse<List<UserItem>>> {
        return flow {
            val response = apiService.getSearchUser(username)
            val dataArray = response.items
            if (dataArray != null) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.toString()))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getDetailUser(username: String): Flow<ApiResponse<DetailResponse>> =
        flow {
            val data = apiService.getDetailUser(username)
            if (data.login == null) {
                emit(ApiResponse.Empty)
            } else {
                emit(ApiResponse.Success(data))
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.toString()))
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<UserItem>>> =
        flow {
            val dataArray = apiService.getUserFollowing(username)
            if (dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.toString()))
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<UserItem>>> =
        flow {
            val dataArray = apiService.getUserFollowers(username)
            if (dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.toString()))
        }.flowOn(Dispatchers.IO)
}