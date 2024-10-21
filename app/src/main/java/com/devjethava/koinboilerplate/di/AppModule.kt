package com.devjethava.koinboilerplate.di

import com.devjethava.koinboilerplate.BuildConfig
import com.devjethava.koinboilerplate.database.AppDatabase
import com.devjethava.koinboilerplate.database.repository.UserRepository
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.helper.ResponseInterceptor
import com.devjethava.koinboilerplate.model.remote.RestApiService
import com.devjethava.koinboilerplate.model.repository.ApiRepository
import com.devjethava.koinboilerplate.model.repository.ApiRepositoryImpl
import com.devjethava.koinboilerplate.viewmodel.DashboardViewModel
import com.devjethava.koinboilerplate.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {

    fun provideRestApi(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
    singleOf(::provideRestApi)
    singleOf(::Preference)
}

val viewModelModule = module {
    viewModelOf(::DashboardViewModel)
    viewModelOf(::UserViewModel)
}

val repositoryModule = module {

    fun provideAPIRepository(api: RestApiService): ApiRepository {
        return ApiRepositoryImpl(api)
    }
    singleOf(::provideAPIRepository)

    // Room Database Repository
    singleOf(::UserRepository)
}

val networkModule = module {
    val connectTimeout: Long = 180// 20s
    val readTimeout: Long = 180 // 20s

    /*
    Http logging client
     */
    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder =
            OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (true) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.addInterceptor(ResponseInterceptor())
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    /*
    Retrofit for API
     */
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build()
    }

    singleOf(::provideHttpClient)
    singleOf(::provideRetrofit)
}

val roomDatabaseModule = module {

    // Room Database
    single {
        AppDatabase.getInstance(androidContext())
    }

    single { get<AppDatabase>().userDAO() }
}

val appModule =
    listOf(apiModule, repositoryModule, viewModelModule, networkModule, roomDatabaseModule)