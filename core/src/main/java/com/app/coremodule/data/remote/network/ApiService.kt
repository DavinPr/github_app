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
        @Query("q") username: String
    ): SearchResponse

    @GET("users")
    suspend fun getTopUser(): List<UserItem>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailResponse

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<UserItem>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): List<UserItem>
}