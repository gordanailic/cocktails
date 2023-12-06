package com.example.cocktails.data.datasources.remote

import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsService {
    @GET("/api/json/v1/1/search.php")
    suspend fun getCocktails(@Query("s") query: String): Response<CocktailResponse>



}