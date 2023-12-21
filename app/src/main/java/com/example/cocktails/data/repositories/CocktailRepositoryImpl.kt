package com.example.cocktails.data.repositories

import androidx.lifecycle.LiveData
import com.example.cocktails.data.datasources.local.CocktailDao
import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailDetailsResponse
import com.example.cocktails.data.models.CocktailResponse
import com.example.cocktails.data.models.FilterResponse
import retrofit2.Response

class CocktailRepositoryImpl(
    private val localDataSource: CocktailDao,
    private val api: CocktailsService
) : CocktailRepository {

    override suspend fun getCocktails(name: String): Response<CocktailResponse> {
        return api.getCocktails(name)
    }

    override suspend fun getCocktailDetails(id: Int): Response<CocktailDetailsResponse> {
        return api.getCocktailDetails(id)
    }

    override suspend fun getSpecificCategory(filterMap: Map<String, String>): Response<FilterResponse> {
        return api.getSpecificCategory(filterMap)
    }

    override suspend fun getCocktailsByFilter(filterMap: Map<String, String>): Response<CocktailResponse> {
        return api.getCocktailsByFilter(filterMap)
    }

    override suspend fun getFavoritesIDs(email: String): List<Int> {
        return localDataSource.getFavoritesIDs(email)
    }

    override fun getFavorites(email: String): LiveData<List<Cocktail>> {
        return localDataSource.getFavorites(email)
    }

    override suspend fun insertCocktail(cocktail: Cocktail): Long {
        return localDataSource.insertCocktail(cocktail)
    }

    override suspend fun deleteCocktail(cocktail: Cocktail): Int {
        return localDataSource.deleteCocktail(cocktail)
    }
}

