package com.app.githubmobile.detail

import androidx.lifecycle.ViewModel
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.coremodule.domain.usecase.model.User

class DetailViewModel(private val useCase: AppUseCase) : ViewModel() {

    fun insertFavorite(user: User) = useCase.insertFavorite(user)
    fun deleteFavorite(user: User) = useCase.deleteFavorite(user)
}