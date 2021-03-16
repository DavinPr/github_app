package com.app.coremodule.data.remote.network

import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.SearchResponse
import com.app.coremodule.data.remote.response.UserItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") username: String,
        @Header("Authorization") token : String = "7547552d4cdb29c66981dc12fd402d2d5eba77fa"
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token : String = "7547552d4cdb29c66981dc12fd402d2d5eba77fa"
    ): DetailResponse

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") token : String = "7547552d4cdb29c66981dc12fd402d2d5eba77fa"
    ): List<UserItem>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") token : String = "7547552d4cdb29c66981dc12fd402d2d5eba77fa"
    ): List<UserItem>
}