package com.example.cocktails.data.models

data class Cocktail(
    val id: Int,
    val name: String,
    val image: Int,
    // TODO: check this
    val favorite: Boolean
)