package com.ayd.recipeapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ayd.recipeapp.model.FoodJoke
import com.ayd.recipeapp.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
    ) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}