package com.app.githubmobile.di

import com.app.coremodule.domain.usecase.AppInteractor
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.githubmobile.detail.DetailViewModel
import com.app.githubmobile.favorite.FavoriteViewModel
import com.app.githubmobile.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AppUseCase> { AppInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}