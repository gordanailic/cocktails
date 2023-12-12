package com.example.cocktails.data.models

sealed class CocktailState {
    data object SuccessAddOrDelete : CocktailState()
    data class Error(val message: String) : CocktailState()
}