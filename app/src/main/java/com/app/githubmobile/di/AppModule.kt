package com.app.githubmobile.di

import com.app.coremodule.domain.usecase.AppInteractor
import com.app.coremodule.domain.usecase.AppUseCase
import com.app.githubmobile.dashboard.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AppUseCase> { AppInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
}