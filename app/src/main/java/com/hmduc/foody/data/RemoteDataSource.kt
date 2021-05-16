package com.hmduc.foody.data

import com.hmduc.foody.data.network.FoodRecipesApi
import com.hmduc.foody.models.FoodJoke
import com.hmduc.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String,String>): Response<FoodRecipe> =
         foodRecipesApi.getRecipes(queries)

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.searchRecipes(searchQuery)

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> =
        foodRecipesApi.getFoodJoke(apiKey)
}