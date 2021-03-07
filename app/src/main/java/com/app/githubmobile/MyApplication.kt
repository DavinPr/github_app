package com.app.githubmobile

import android.app.Application
import com.app.coremodule.di.jsonModule
import com.app.coremodule.di.repositoryModule
import com.app.githubmobile.di.useCaseModule
import com.app.githubmobile.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                jsonModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}