package com.ayd.recipeapp.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.ayd.recipeapp.data.Repo
import com.ayd.recipeapp.data.database.entities.FavoritesEntity
import com.ayd.recipeapp.data.database.entities.RecipesEntity
import com.ayd.recipeapp.model.FoodRecipe
import com.ayd.recipeapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo, application: Application
): AndroidViewModel(application) {


    /** room database */

    val readRecipes: LiveData<List<RecipesEntity>> = repo.local.readRecipes().asLiveData() // asLiveData flow'u livedata'ya çevirir. repo local'de flow tutmuştuk, livedataya çevirdik.
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repo.local.readFavoriteRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)=
        viewModelScope.launch(Dispatchers.IO) {
            repo.local.insertFavoriteRecipes(favoritesEntity)
        }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)=
        viewModelScope.launch(Dispatchers.IO) {
            repo.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFavoriteRecipe()=
        viewModelScope.launch(Dispatchers.IO) {
            repo.local.deleteAllFavoriteRecipes()
        }

    /** retrofit*/

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String,String>) = viewModelScope.launch{
        searchRecipesSafeCall(searchQuery)
    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        recipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try{
                val response = repo.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if(foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }

            }catch (e:Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found")
            }

        }else{
            recipesResponse.value = NetworkResult.Error("connection error")
        }

    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {

        searchedRecipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try{
                val response = repo.remote.searchRecipes(searchQuery)
                searchedRecipesResponse.value = handleFoodRecipesResponse(response)

            }catch (e:Exception){
                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found")
            }

        }else{
            searchedRecipesResponse.value = NetworkResult.Error("connection error")
        }


    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 ->{
                return NetworkResult.Error("Api key error")
            }
            response.body()!!.results.isNullOrEmpty()->{
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful->{
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}