package com.app.githubmobile.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.coremodule.domain.usecase.AppUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class HomeViewModel(private val useCase: AppUseCase) : ViewModel() {

    val getTopUser = useCase.getTopUser().asLiveData()

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    val searchResult = queryChannel.asFlow()
        .debounce(1000)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            useCase.getSearchUser(it).asLiveData()
        }.asLiveData()

    fun getAllRecent() = useCase.getAllRecent().asLiveData()

    val getLanguage = useCase.getLocale()

    fun deleteRecent(username: String) = useCase.deleteRecent(username)
}