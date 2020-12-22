package com.hmduc.foody.data

import com.hmduc.foody.data.network.FoodRecipesApi
import com.hmduc.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String,String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}