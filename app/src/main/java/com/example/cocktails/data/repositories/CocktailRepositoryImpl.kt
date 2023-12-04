package com.example.cocktails.data.repositories

import com.example.cocktails.data.datasources.remote.CocktailsService
import com.example.cocktails.data.models.CocktailResponse
import retrofit2.Response

class CocktailRepositoryImpl(
    private val api: CocktailsService
) : CocktailRepository {

    override suspend fun fetchAll(): Response<CocktailResponse> {
        return api.getCocktailByName()
    }
}

