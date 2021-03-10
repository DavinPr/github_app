package com.app.coremodule.data

import com.app.coremodule.data.remote.ApiResponse
import com.app.coremodule.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val appExecutors: AppExecutors) {

    private val result: Flow<Resource<ResultType>> =
        flow {
            emit(Resource.Loading())
            val dbSource = loadFromDB().first()
            if (shouldFetch(dbSource)) {
                emit(Resource.Loading())
                when (val apiResponse = createCall().first()) {
                    is ApiResponse.Success -> {
                        if (apiResponse.data != null) {
                            emit(Resource.Success(callResponse()))
                        }
                    }

                    is ApiResponse.Empty -> emit(Resource.Success(callResponse()))

                    is ApiResponse.Error -> {
                        onFetchFailed()
                        emit(
                            Resource.Error<ResultType>(
                                apiResponse.errorMessage
                            )
                        )
                    }
                }
            } else {
                emitAll(loadFromDB().map {
                    Resource.Success(
                        it
                    )
                })
            }
        }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun callResponse(): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result
}