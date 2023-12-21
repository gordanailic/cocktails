package com.example.cocktails.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "cocktail", primaryKeys = ["id", "email"])
data class Cocktail(


    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strAlcoholic") var alcoholic: String?,
    var favorite: Boolean,
    var email: String
)