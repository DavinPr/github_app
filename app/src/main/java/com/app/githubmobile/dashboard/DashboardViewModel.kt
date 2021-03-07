package com.app.githubmobile.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.coremodule.domain.usecase.model.User

class DashboardViewModel(private val useCase: AppUseCase) : ViewModel() {
    fun getAllUser(): LiveData<Resource<List<User>>> = useCase.getAllUser().asLiveData()
}