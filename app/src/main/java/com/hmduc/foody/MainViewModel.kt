package com.hmduc.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmduc.foody.data.Repository
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: MyApplication
) :
    AndroidViewModel(application) {

    var recipesReponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesReponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val reponse = repository.remote.getRecipes(queries)
                recipesReponse.value = handleRecipesReponse(reponse)
            } catch (ex: Exception) {
                recipesReponse.value = NetworkResult.Error("Recipes is not found")
            }
        } else {
            recipesReponse.value = NetworkResult.Error("No Internet Connection!!")
        }
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