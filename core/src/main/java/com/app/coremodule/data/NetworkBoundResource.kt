package com.app.coremodule.data

import com.app.coremodule.data.remote.network.ApiResponse
import com.app.coremodule.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val appExecutors: AppExecutors) {

    private val result: Flow<Resource<ResultType>> =
        flow {
            emit(Resource.Loading())
            val dbSource = loadFromDB().first()
            if (shouldFetch(dbSource)) {
                emit(Resource.Loading())
                emitAll(callResponse())
            } else {
                emitAll(loadFromDB().map {
                    Resource.Success(it)
                })
            }
        }.flowOn(Dispatchers.IO)

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun callResponse(): Flow<Resource<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = result

}