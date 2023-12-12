package com.example.cocktails.modules

import android.content.Context
import androidx.room.Room
import com.example.cocktails.Constants
import com.example.cocktails.data.datasources.local.CocktailDao
import com.example.cocktails.data.datasources.local.CocktailDataBase
import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.data.repositories.CocktailRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCocktailRepository(
        api: CocktailsService,
        localDataSource: CocktailDao
    ): CocktailRepository = CocktailRepositoryImpl(localDataSource, api)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

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
    fun provideConstants(): Constants = Constants


    @Provides
    @Singleton
    fun provideMyApi(
        gson: Gson,
        httpClient: OkHttpClient,
        constants: Constants
    ): CocktailsService {
        return Retrofit.Builder()
            .baseUrl(constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
            .create(CocktailsService::class.java)
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideCocktailDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CocktailDataBase::class.java,
        "CocktailsDb"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideCocktailDao(db: CocktailDataBase) =
        db.getCocktailDao() // The reason we can implement a Dao for the database

}