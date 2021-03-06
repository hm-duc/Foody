package com.hmduc.foody.data.database.enities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}