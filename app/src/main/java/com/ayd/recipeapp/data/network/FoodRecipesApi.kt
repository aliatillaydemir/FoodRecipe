package com.ayd.recipeapp.data.network

import com.ayd.recipeapp.model.FoodJoke
import com.ayd.recipeapp.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface  FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String,String>
    ):Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String,String>
    ):Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getFoodJoke(         //sadece bir query gönderdirdiğimiz için üsttekiler gibi map göndermeye gerek yok.
        @Query("apiKey") apiKey:String
    ):Response<FoodJoke>

}