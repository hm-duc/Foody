package com.hmduc.foody.data.database.enities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hmduc.foody.models.Result
import com.hmduc.foody.util.Constants.Companion.FAVORITES_TABLE

@Entity(tableName = FAVORITES_TABLE)
class FavoritesEnity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var result: Result
)