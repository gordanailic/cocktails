package com.example.cocktails.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "cocktail")
data class Cocktail(
    @PrimaryKey
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strAlcoholic") var alcoholic: String?,
    var favorite: Boolean
)