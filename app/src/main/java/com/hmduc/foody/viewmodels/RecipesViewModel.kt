package com.hmduc.foody.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hmduc.foody.data.DataStroreRepository
import com.hmduc.foody.util.Constants
import com.hmduc.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.hmduc.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.hmduc.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor (application: Application, private val dataStroreRepository: DataStroreRepository) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    val readMealAndDiet = dataStroreRepository.readMealAndDiet

    fun saveMealAndDiet(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStroreRepository.saveMealAndDiet(mealType, mealTypeId, dietType, dietTypeId)
        }

     fun applyQueries(): HashMap<String, String> {
         viewModelScope.launch {
             readMealAndDiet.collect { value ->
                 mealType = value.selectedMealType
                 dietType = value.selectedDietType
             }
         }

        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}