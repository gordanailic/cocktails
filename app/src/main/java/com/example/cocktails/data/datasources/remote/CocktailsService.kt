package com.example.cocktails.data.datasources.remote

import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET

interface CocktailsService {
    @GET("search.php?s=margarita")
    suspend fun getCocktailByName(): Response<CocktailResponse>
}