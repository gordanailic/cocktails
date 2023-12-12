package com.example.cocktails.data.repositories

import androidx.lifecycle.LiveData
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response

interface CocktailRepository {

    suspend fun getCocktails(name: String): Response<CocktailResponse>
    suspend fun getFavoritesIDs(): List<Int>

    fun getFavorites(): LiveData<List<Cocktail>>

    suspend fun insertCocktail(cocktail: Cocktail): Long
    suspend fun deleteCocktail(cocktail: Cocktail): Int


}