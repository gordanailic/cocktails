package com.example.cocktails.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktails.data.models.Cocktail

@Database(
    entities = [Cocktail::class],
    version = 1,
    exportSchema = false
)
abstract class CocktailDataBase : RoomDatabase() {
    abstract fun getCocktailDao(): CocktailDao

}