package com.app.coremodule.data

import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.domain.repository.IAppRepository
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User
import com.app.coremodule.utils.AppExecutors
import com.app.coremodule.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AppRepository(
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAppRepository {

    override fun getSearchUser(username: String): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getSearchUser(username).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapUserResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<User>>(listOf()))
                is ApiResponse.Error -> emit(Resource.Error<List<User>>(apiResponse.errorMessage))
            }
        }.flowOn(Dispatchers.IO)


    override fun getDetailUser(username: String): Flow<Resource<Detail>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getDetailUser(username).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapDetailResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success(Detail()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(apiResponse.errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getUserFollowing(username).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapUserResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<User>>(listOf()))
                is ApiResponse.Error -> emit(Resource.Error<List<User>>(apiResponse.errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    override fun getUserFollowers(username: String): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getUserFollowers(username).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapUserResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<User>>(listOf()))
                is ApiResponse.Error -> emit(Resource.Error<List<User>>(apiResponse.errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    override fun getAllFavorite(): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            val data = localDataSource.getAllFavorite().map {
                DataMapper.mapEntityToDomain(it)
            }
            emitAll(data.map {
                Resource.Success(it)
            })
        }

    override fun insertFavorite(user: User) {
        val favorite = DataMapper.mapDomainToEntity(user)
        appExecutors.diskIO().execute {
            localDataSource.insertFavorite(favorite)
        }
    }

    override fun deleteFavorite(user: User) {
        val favorite = DataMapper.mapDomainToEntity(user)
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(favorite)
        }
    }

    override fun putFragmentTag(tag: String) {
        appExecutors.diskIO().execute {
            localDataSource.putFragmentTag(tag)
        }
    }

    override fun getFragmentTag(): String? = localDataSource.getFragmentTag()

}