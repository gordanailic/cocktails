package com.example.cocktails.data.repositories

import androidx.lifecycle.LiveData
import com.example.cocktails.data.datasources.local.CocktailDao
import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response

class CocktailRepositoryImpl(
    private val localDataSource: CocktailDao,
    private val api: CocktailsService
) : CocktailRepository {

    override suspend fun getCocktails(name: String): Response<CocktailResponse> {
        return api.getCocktails(name)
    }

    override suspend fun getCocktailsById(): List<Int> {
        return localDataSource.getCocktailsById()
    }

    override fun getFavorites(): LiveData<List<Cocktail>> {
        return localDataSource.getFavorites()
    }

    override suspend fun insertCocktail(cocktail: Cocktail): Long {
        return localDataSource.insertCocktail(cocktail)
    }

    override suspend fun deleteCocktail(cocktail: Cocktail): Int {
        return localDataSource.deleteCocktail(cocktail)
    }
}

