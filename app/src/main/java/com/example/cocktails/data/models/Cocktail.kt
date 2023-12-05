package com.example.cocktails.data.models

import com.google.gson.annotations.SerializedName


data class Cocktail(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strAlcoholic") val alcoholic: String,
    val favorite: Boolean
)