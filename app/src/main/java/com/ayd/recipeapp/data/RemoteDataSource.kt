package com.ayd.recipeapp.data

import com.ayd.recipeapp.data.network.FoodRecipesApi
import com.ayd.recipeapp.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

   suspend fun getRecipes(queries: Map<String,String>): Response<FoodRecipe>{
       return foodRecipesApi.getRecipes(queries)
    }

}