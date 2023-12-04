package com.example.cocktails.modules

import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.data.repositories.CocktailRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyRepository(api: CocktailsService): CocktailRepository {
        return CocktailRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)

        return httpClient.build()

    }

    @Provides
    @Singleton
    fun provideMyApi(
        gson: Gson,
        httpClient: OkHttpClient
    ): CocktailsService {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()
            .create(CocktailsService::class.java)
    }
    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree {
        return Timber.DebugTree()
    }

}