package com.example.cocktails.presentation.fragments.favorites.viewmodel

sealed class CocktailState {
    data object SuccessAddOrDelete : CocktailState()
    data class Error(val message: String) : CocktailState()
}