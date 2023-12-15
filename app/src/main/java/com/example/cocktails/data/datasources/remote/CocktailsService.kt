package com.example.cocktails.data.datasources.remote

import com.example.cocktails.data.models.CocktailResponse
import com.example.cocktails.data.models.FilterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CocktailsService {
    @GET("/api/json/v1/1/search.php")
    suspend fun getCocktails(@Query("s") query: String): Response<CocktailResponse>
    @GET("/api/json/v1/1/list.php")
    suspend fun getSpecificCategory(@QueryMap params: Map<String, String>): Response<FilterResponse>
}
