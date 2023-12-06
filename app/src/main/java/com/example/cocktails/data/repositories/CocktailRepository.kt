package com.example.cocktails.data.repositories

import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response

interface CocktailRepository {

    suspend fun searchByName(name: String): Response<CocktailResponse>


}