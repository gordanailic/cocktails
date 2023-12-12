package com.example.cocktails.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktails.data.models.Cocktail

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: Cocktail): Long

    @Query("SELECT * FROM cocktail ORDER BY alcoholic")
    fun getFavorites(): LiveData<List<Cocktail>>

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail): Int

    @Query("SELECT id FROM cocktail")
    suspend fun getCocktailsById(): List<Int>
}