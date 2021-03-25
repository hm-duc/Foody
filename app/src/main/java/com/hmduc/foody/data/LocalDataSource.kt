package com.hmduc.foody.data

import com.hmduc.foody.data.database.RecipesDao
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.data.database.enities.RecipesEnity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao){
    suspend fun insertRecipes(recipesEnity: RecipesEnity) {
        recipesDao.insertRecipes(recipesEnity)
    }

    suspend fun insertFavorites(favoritesEnity: FavoritesEnity) {
        recipesDao.insertFavorites(favoritesEnity)
    }

    fun readRecipes(): Flow<List<RecipesEnity>> {
        return recipesDao.readRecipes()
    }

    fun readFavorites(): Flow<List<FavoritesEnity>> {
        return recipesDao.readFavorites()
    }

    suspend fun deleteFavorites(favoritesEnity: FavoritesEnity) = recipesDao.deleteFavorites(favoritesEnity)

    suspend fun deleteAllFavorites() = recipesDao.deleteAllFavorites()
}