package com.ayd.recipeapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ayd.recipeapp.model.Result
import com.ayd.recipeapp.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)