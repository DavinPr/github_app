package com.app.coremodule.data

import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.SearchItem
import com.app.coremodule.data.remote.response.UserFollowResponse
import com.app.coremodule.domain.repository.IAppRepository
import com.app.coremodule.domain.usecase.model.User
import com.app.coremodule.utils.AppExecutors
import com.app.coremodule.utils.DataMapper
import kotlinx.coroutines.flow.*

class AppRepository(
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAppRepository {
    override fun getSearchUser(username: String): Flow<Resource<List<SearchItem>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getSearchUser(username).first()) {
                is ApiResponse.Success -> {

                }
                is ApiResponse.Empty -> emit(Resource.Success<List<SearchItem>>(emptyList()))
                is ApiResponse.Error -> {
                }
            }
        }

    override fun getDetailUser(username: String): Flow<Resource<DetailResponse>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowing(username: String): Flow<Resource<List<UserFollowResponse>>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowers(username: String): Flow<Resource<List<UserFollowResponse>>> {
        TODO("Not yet implemented")
    }


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

}