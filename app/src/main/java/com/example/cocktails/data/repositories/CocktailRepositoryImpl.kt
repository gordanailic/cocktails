package com.example.cocktails.data.repositories

import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response

class CocktailRepositoryImpl(
    private val api: CocktailsService
) : CocktailRepository {

    override suspend fun getCocktails(name: String): Response<CocktailResponse> {
        return api.getCocktails(name)
    }
}

