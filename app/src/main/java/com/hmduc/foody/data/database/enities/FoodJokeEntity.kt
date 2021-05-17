package com.hmduc.foody.data.database.enities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hmduc.foody.models.FoodJoke
import com.hmduc.foody.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}