package com.app.coremodule.data.remote.network

import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.SearchResponse
import com.app.coremodule.data.remote.response.UserFollowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchUser(@Query("q") username: String): SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): DetailResponse

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<UserFollowResponse>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<UserFollowResponse>

}