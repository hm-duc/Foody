package com.hmduc.foody.data.database

import androidx.room.*
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.data.database.enities.RecipesEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEnity: RecipesEnity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favoritesEnity: FavoritesEnity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEnity>>

    @Query("SELECT * FROM favorities_table ORDER BY id ASC")
    fun readFavorites(): Flow<List<FavoritesEnity>>

    @Delete
    suspend fun deleteFavorites(favoritesEnity: FavoritesEnity)

    @Query("DELETE FROM favorities_table")
    suspend fun deleteAllFavorites()
}