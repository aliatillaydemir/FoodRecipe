package com.ayd.recipeapp.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ayd.recipeapp.data.DataStoreRepo
import com.ayd.recipeapp.util.Constants
import com.ayd.recipeapp.util.Constants.Companion.API_KEY
import com.ayd.recipeapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.ayd.recipeapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.ayd.recipeapp.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.ayd.recipeapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.ayd.recipeapp.util.Constants.Companion.QUERY_API_KEY
import com.ayd.recipeapp.util.Constants.Companion.QUERY_DIET
import com.ayd.recipeapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.ayd.recipeapp.util.Constants.Companion.QUERY_NUMBER
import com.ayd.recipeapp.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepo: DataStoreRepo
    ) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepo.readMealAndDietType
    val readBackOnline = dataStoreRepo.readBackOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO ) {
            dataStoreRepo.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String,String>{
        val queries: HashMap<String,String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect{value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"


        return queries
    }

    fun showNetworkStatus(){
        if(!networkStatus){
        Toast.makeText(getApplication(),"No Internet", Toast.LENGTH_SHORT).show()
        saveBackOnline(true)
        } else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(),"back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }

    }

}