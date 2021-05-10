package com.hmduc.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.data.database.enities.RecipesEnity

@Database(
    entities = [RecipesEnity::class, FavoritesEnity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}