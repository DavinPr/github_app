package com.app.coremodule.di

import androidx.room.Room
import com.app.coremodule.data.AppRepository
import com.app.coremodule.data.local.LocalDataSource
import com.app.coremodule.data.local.room.AppDatabase
import com.app.coremodule.data.remote.RemoteDataSource
import com.app.coremodule.data.remote.network.ApiService
import com.app.coremodule.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<AppDatabase>().appDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "github.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    factory { AppExecutors() }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single { AppRepository(get(), get(), get()) }
}