package com.hmduc.foody.data.database

import androidx.room.Database
import androidx.room.TypeConverters

@Database(
    entities = [RecipesEnity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase {
    abstract fun recipeDao(): RecipesDao
}