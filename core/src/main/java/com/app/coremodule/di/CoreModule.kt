package com.app.coremodule.di

import com.app.coremodule.data.AppRepository
import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.remote.JsonHelper
import com.app.coremodule.data.remote.JsonService
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.utils.AppExecutors
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val jsonModule = module {
    single {
        Gson()
    }
    single {
        val filename = "githubuser.json"
        androidContext().assets
            .open(filename)
            .bufferedReader()
            .use { it.readText() }
    }

    single<JsonService> { JsonHelper(get(), get()) }
}

val repositoryModule = module {
    factory { AppExecutors() }
    single { RemoteDataSource(get()) }
    single { LocalDataSource() }
    single { AppRepository(get(), get(), get()) }
}