package com.app.githubmobile.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.coremodule.domain.usecase.model.User

class DetailViewModel(private val useCase: AppUseCase) : ViewModel() {

    fun getDetailData(username: String) = useCase.getDetailUser(username).asLiveData()
    fun getUserFollower(username: String) = useCase.getUserFollowers(username).asLiveData()
    fun getUserFollowing(username: String) = useCase.getUserFollowing(username).asLiveData()

    fun insertFavorite(user: User) = useCase.insertFavorite(user)
    fun deleteFavorite(user: User) = useCase.deleteFavorite(user)

    fun putDetailFragmentTag(tag: String) = useCase.putDetailFragmentTag(tag)
    fun getDetailFragmentTag(): String? = useCase.getFragmentTag()
}