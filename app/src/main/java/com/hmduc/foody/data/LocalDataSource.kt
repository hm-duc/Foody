package com.hmduc.foody.data

import com.hmduc.foody.data.database.RecipesDao
import com.hmduc.foody.data.database.enities.FavoritesEntity
import com.hmduc.foody.data.database.enities.FoodJokeEntity
import com.hmduc.foody.data.database.enities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao){
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavorites(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavorites(favoritesEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavorites(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavorites()
    }

    fun readFoodJokes(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJokes()
    }

    suspend fun deleteFavorites(favoritesEntity: FavoritesEntity) = recipesDao.deleteFavorites(favoritesEntity)

    suspend fun deleteAllFavorites() = recipesDao.deleteAllFavorites()
}