package com.example.cocktails.data.repositories

import androidx.lifecycle.LiveData
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailDetailsResponse
import com.example.cocktails.data.models.CocktailResponse
import com.example.cocktails.data.models.FilterResponse
import retrofit2.Response

interface CocktailRepository {

    suspend fun getCocktails(name: String): Response<CocktailResponse>
    suspend fun getCocktailDetails(id: Int): Response<CocktailDetailsResponse>

    suspend fun getSpecificCategory(filterMap: Map<String, String>): Response<FilterResponse>
    suspend fun getCocktailsByFilter(filterMap: Map<String, String>): Response<CocktailResponse>
    suspend fun getFavoritesIDs(email: String): List<Int>

    fun getFavorites(email: String): LiveData<List<Cocktail>>

    suspend fun insertCocktail(cocktail: Cocktail): Long
    suspend fun deleteCocktail(cocktail: Cocktail): Int


}