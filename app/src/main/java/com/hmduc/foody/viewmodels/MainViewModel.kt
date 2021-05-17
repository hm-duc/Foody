package com.hmduc.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hmduc.foody.data.Repository
import com.hmduc.foody.data.database.enities.FavoritesEntity
import com.hmduc.foody.data.database.enities.FoodJokeEntity
import com.hmduc.foody.data.database.enities.RecipesEntity
import com.hmduc.foody.models.FoodJoke
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {

    // Room database
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavorites: LiveData<List<FavoritesEntity>> =
        repository.local.readFavorites().asLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJokes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }

    fun insertFavorites(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavorites(favoritesEntity)
        }
    }

    fun deleteFavorites(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavorites(favoritesEntity)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavorites()
        }
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }
    }

    // Retrofit
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val reponse = repository.remote.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleRecipesReponse(reponse)
            } catch (ex: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Recipes is not found")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val reponse = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeReponse(reponse)

                val foodJoke = foodJokeResponse.value!!.data
                if (foodJoke != null) {
                    offlineCacheFoodJoke(foodJoke)
                }
            } catch (ex: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Recipes is not found")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val reponse = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipesReponse(reponse)

                val foodRecipes = recipesResponse.value!!.data
                if (foodRecipes != null) {
                    offlineCacheRecipe(foodRecipes)
                }
            } catch (ex: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes is not found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    private fun offlineCacheRecipe(foodRecipes: FoodRecipe) {
        val recipesEnity = RecipesEntity(foodRecipes)
        insertRecipes(recipesEnity)
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

     private fun handleRecipesReponse(reponse: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return when {
            reponse.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            reponse.code() == 402 -> NetworkResult.Error("Api Key limmited")
            reponse.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("Recipoes is not found")
            reponse.isSuccessful -> NetworkResult.Success(reponse.body()!!)
            else -> NetworkResult.Error(reponse.message())
        }
    }

    private fun handleFoodJokeReponse(reponse: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            reponse.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            reponse.code() == 402 -> NetworkResult.Error("Api Key limmited")
            reponse.isSuccessful -> NetworkResult.Success(reponse.body()!!)
            else -> NetworkResult.Error(reponse.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}