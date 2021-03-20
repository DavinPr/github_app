package com.app.githubmobile.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.coremodule.domain.usecase.model.Detail

class DetailViewModel(private val useCase: AppUseCase) :
    ViewModel() {

    fun getDetailData(username: String) = useCase.getDetailUser(username).asLiveData()

    fun getUserFollower(username: String) = useCase.getUserFollowers(username).asLiveData()
    fun getUserFollowing(username: String) = useCase.getUserFollowing(username).asLiveData()

    fun insertFavorite(detail: Detail) = useCase.insertFavorite(detail)
    fun deleteFavorite(detail: Detail) = useCase.deleteFavorite(detail)

    fun insertRecent(detail: Detail) = useCase.insertRecent(detail)
}