package com.hmduc.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.models.Result

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe): String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listTyle = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listTyle)
    }

    @TypeConverter
    fun resultToString(result: Result): String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listTyle = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listTyle)
    }
}