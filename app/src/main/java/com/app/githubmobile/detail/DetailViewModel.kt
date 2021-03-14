package com.app.githubmobile.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User

class DetailViewModel(private val state: SavedStateHandle, private val useCase: AppUseCase) :
    ViewModel() {

    fun getDetailData(username: String) = useCase.getDetailUser(username).asLiveData()
    fun putDetailDataState(detail: Detail) {
        if (state.contains("detail_data")){
            state.remove<Detail>("detail_data")
        }
        state["detail_data"] = detail
    }

    val getDetailDataState = state.getLiveData<Detail>("detail_data")

    val clearState = state.remove<Detail>("detail_data")

    fun getUserFollower(username: String) = useCase.getUserFollowers(username).asLiveData()
    fun getUserFollowing(username: String) = useCase.getUserFollowing(username).asLiveData()

    fun insertFavorite(user: User) = useCase.insertFavorite(user)
    fun deleteFavorite(user: User) = useCase.deleteFavorite(user)

    fun putDetailFragmentTag(tag: String) = useCase.putDetailFragmentTag(tag)
    fun getDetailFragmentTag(): String? = useCase.getFragmentTag()
}