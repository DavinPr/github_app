package com.app.coremodule.data.remote

import com.app.coremodule.data.remote.response.UsersItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val jsonService: JsonService) {
    suspend fun getAllUsers(): Flow<ApiResponse<List<UsersItem>?>> = flow {
        try {
            val response = jsonService.getUser()
            val dataArray = response.users

            if (dataArray != null) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            ex.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}