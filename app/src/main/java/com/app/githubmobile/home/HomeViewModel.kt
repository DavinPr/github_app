package com.app.githubmobile.home

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

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    val searchResult = queryChannel.asFlow()
        .debounce(1000)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            useCase.getSearchUser(it)
        }.asLiveData()

    fun putFragmentTag(tag: String) = useCase.putFragmentTag(tag)

    val getFragmentTag: String? = useCase.getFragmentTag()
}