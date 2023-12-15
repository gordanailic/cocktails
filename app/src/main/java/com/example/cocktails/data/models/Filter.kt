package com.example.cocktails.data.models

import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("strAlcoholic") val alcoholic: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strGlass") val glass: String,
    @SerializedName("strIngredient1") val ingredient: String,
    var filterParam: String = "a"
) {
    fun getLabel(): String {
        return when (filterParam) {
            "a" -> alcoholic
            "c" -> category
            "g" -> glass
            "i" -> ingredient
            else -> {
                alcoholic
            }
        }
    }
}