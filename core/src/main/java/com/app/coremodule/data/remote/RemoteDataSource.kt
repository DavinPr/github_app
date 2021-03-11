package com.app.coremodule.data.remote

import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.data.remote.network.ApiService
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.SearchItem
import com.app.coremodule.data.remote.response.UserFollowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getSearchUser(username: String): Flow<ApiResponse<List<SearchItem>>> =
        flow {
            try {
                val response = apiService.getSearchUser(username)
                val dataArray = response.items
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<DetailResponse>> =
        flow {
            try {
                val data = apiService.getDetailUser(username)
                emit(ApiResponse.Success(data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<UserFollowResponse>>> =
        flow {
            try {
                val dataArray = apiService.getUserFollowing(username)
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<UserFollowResponse>>> =
        flow {
            try {
                val dataArray = apiService.getUserFollowers(username)
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
}