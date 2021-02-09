package com.hmduc.foody.data

import com.hmduc.foody.data.database.RecipesDao
import com.hmduc.foody.data.database.RecipesEnity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao){
    suspend fun insertRecipes(recipesEnity: RecipesEnity) {
        recipesDao.insertRecipes(recipesEnity)
    }

    fun readDatabase(): Flow<List<RecipesEnity>> {
        return recipesDao.readRecipes()
    }
}