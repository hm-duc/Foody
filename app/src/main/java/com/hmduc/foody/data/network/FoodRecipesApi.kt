package com.hmduc.foody.data.network

import com.hmduc.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.time.temporal.TemporalQueries

interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String,String>): Response<FoodRecipe>
}