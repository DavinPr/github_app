package com.app.githubmobile.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase

class FavoriteViewModel(private val useCase: AppUseCase) : ViewModel() {
    fun getAllFavorite() = useCase.getAllFavorite().asLiveData()
}