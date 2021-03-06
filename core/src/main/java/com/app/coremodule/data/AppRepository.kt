package com.app.coremodule.data

import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.remote.ApiResponse
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.domain.repository.IAppRepository
import com.app.coremodule.domain.usecase.model.User
import com.app.coremodule.utils.AppExecutors
import com.app.coremodule.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AppRepository(
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAppRepository {
    override fun getAllUsers(): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getAllUsers().first()) {
                is ApiResponse.Success -> {
                    if (apiResponse.data != null) {
                        val data = DataMapper.mapResponseToDomain(apiResponse.data)
                        emit(Resource.Success(data))
                    }
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<User>>(emptyList()))
                is ApiResponse.Error -> emit(Resource.Error<List<User>>(apiResponse.errorMessage))
            }
        }

}