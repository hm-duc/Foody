package com.hmduc.foody.data.database

import androidx.room.*
import com.hmduc.foody.data.database.enities.FavoritesEntity
import com.hmduc.foody.data.database.enities.FoodJokeEntity
import com.hmduc.foody.data.database.enities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favoritesEntity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorities_table ORDER BY id ASC")
    fun readFavorites(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJokes(): Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteFavorites(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorities_table")
    suspend fun deleteAllFavorites()


}