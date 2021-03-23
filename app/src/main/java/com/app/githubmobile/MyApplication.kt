package com.app.githubmobile

import android.app.Application
import com.app.coremodule.di.databaseModule
import com.app.coremodule.di.networkModule
import com.app.coremodule.di.repositoryModule
import com.app.githubmobile.di.useCaseModule
import com.app.githubmobile.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

class MyApplication : Application() {


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    databaseModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}