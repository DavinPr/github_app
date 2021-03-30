package com.app.githubmobile.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase

class FavoriteViewModel(private val useCase: AppUseCase) : ViewModel() {
    fun getAllFavorite() = useCase.getAllFavorite().asLiveData()
    fun deleteFavoriteByUsername(username: String) = useCase.deleteFavoriteByUsername(username)
}