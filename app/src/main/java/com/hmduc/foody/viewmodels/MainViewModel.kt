package com.hmduc.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import coil.request.Disposable
import com.hmduc.foody.data.Repository
import com.hmduc.foody.data.database.RecipesEnity
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // Room database
    val readRecipes: LiveData<List<RecipesEnity>> = repository.local.readDatabase().asLiveData()
    private fun insertRecipes(recipesEnity: RecipesEnity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEnity)
        }
    }
    // Retrofit
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

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
                    offlineCache(foodRecipes)
                }
            } catch (ex: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes is not found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    private fun offlineCache(foodRecipes: FoodRecipe) {
        val recipesEnity = RecipesEnity(foodRecipes)
        insertRecipes(recipesEnity)
    }

    private fun handleRecipesReponse(reponse: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        return when {
            reponse.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            reponse.code() == 402 -> NetworkResult.Error("Api Key limmited")
            reponse.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("Recipoes is not found")
            reponse.isSuccessful -> NetworkResult.Success(reponse.body()!!)
            else -> NetworkResult.Error(reponse.message())
        }
    }

    private fun hasInternetConnection() : Boolean {
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