package com.app.coremodule.data

import android.database.Cursor
import android.util.Log
import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.domain.repository.IAppRepository
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.Recent
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

    override fun getTopUser(): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getTopUser().first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapUserResponseToDomain(apiResponse.data)
                    data.map { user ->
                        remoteDataSource.getDetailUser(user.username).collect {
                            when (it) {
                                is ApiResponse.Success -> {
                                    val detail = it.data
                                    user.name = detail.name
                                    user.followers = detail.followers
                                    user.following = detail.following
                                }
                                is ApiResponse.Empty -> {
                                    user.name = "No name"
                                    user.followers = 0
                                    user.following = 0
                                }
                                is ApiResponse.Error -> {
                                    Log.d("Top user detail", "Error when getting data")
                                }
                            }
                        }

                    }
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<User>>(listOf()))
                is ApiResponse.Error -> emit(Resource.Error<List<User>>(apiResponse.errorMessage))
            }
        }.flowOn(Dispatchers.IO)


    override fun getDetailUser(username: String): Flow<Resource<Detail>> =
        object : NetworkBoundResource<Detail, DetailResponse>(appExecutors) {
            override fun loadFromDB(): Flow<Detail> =
                localDataSource.getDetailFavorite(username).map {
                    DataMapper.mapDetailEntityToDomain(it)
                }

            override fun shouldFetch(data: Detail?): Boolean {
                return data?.login == null
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailResponse>> =
                remoteDataSource.getDetailUser(username)

            override suspend fun callResponse(): Flow<Resource<Detail>> =
                flow {
                    emit(Resource.Loading())
                    when (val apiResponse = remoteDataSource.getDetailUser(username).first()) {
                        is ApiResponse.Success -> {
                            val data = DataMapper.mapDetailResponseToDomain(apiResponse.data)
                            emit(Resource.Success(data))
                        }
                        is ApiResponse.Empty -> {
                            emit(Resource.Success(Detail()))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error<Detail>(apiResponse.errorMessage))
                        }
                    }
                }
        }.asFlow()

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
                DataMapper.mapFavoriteListEntityToDomain(it)
            }
            emitAll(data.map {
                Resource.Success(it)
            })
        }

    override fun insertFavorite(detail: Detail) {
        val favorite = DataMapper.mapDomainToFavoriteEntity(detail)
        appExecutors.diskIO().execute {
            localDataSource.insertFavorite(favorite)
        }
    }

    override fun deleteFavorite(detail: Detail) {
        val favorite = DataMapper.mapDomainToFavoriteEntity(detail)
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(favorite)
        }
    }

    override fun deleteFavoriteByUsername(username: String) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavoriteByUsername(username)
        }
    }

    override fun getAllRecent(): Flow<List<Recent>> =
        flow {
            val data = localDataSource.getAllRecent().map {
                DataMapper.mapRecentEntityToDomain(it)
            }
            emitAll(data)
        }.flowOn(Dispatchers.IO)

    override fun insertRecent(detail: Detail) {
        val recent = DataMapper.mapDomainToRecentEntity(detail)
        appExecutors.diskIO().execute {
            localDataSource.insertRecent(recent)
            localDataSource.deleteRecent()
        }
    }

    override fun deleteRecent(username: String) {
        appExecutors.diskIO().execute {
            localDataSource.deleteRecentByUsername(username)
        }
    }

    override fun getLocale(): String? {
        return localDataSource.getLocale()
    }

    override fun getAllFavoriteCursor(): Cursor {
        return localDataSource.getAllFavoriteCursor()
    }
}