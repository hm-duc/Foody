package com.hmduc.foody.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "c318a5f04f5946419de0e1c354a0e72d"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // Bottom sheet and Preferences
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val PREFERENCE_NAME = "food preferences"
        const val PREFERENCE_MEAL_TYPE = "mealType"
        const val PREFERENCE_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCE_DIET_TYPE = "dietType"
        const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"
    }
}