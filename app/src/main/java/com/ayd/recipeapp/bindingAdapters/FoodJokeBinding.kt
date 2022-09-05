package com.ayd.recipeapp.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ayd.recipeapp.data.database.entities.FoodJokeEntity
import com.ayd.recipeapp.model.FoodJoke
import com.ayd.recipeapp.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object{

        @BindingAdapter("readApiResponse3","readDatabase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse:NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            when(apiResponse){
                is NetworkResult.Loading -> {
                    when(view) {
                        is ProgressBar ->{
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView ->{
                           view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                            if(database != null){
                                if(database.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success ->{
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }


                else -> {} // eklememiz gerekiyor when için ama işlevsel gerekliliği yok, bütün olasılıkları yazdık zaten.
            }

        }

        @BindingAdapter("readApiResponse4","readDatabase4")
        @JvmStatic
        fun setErrorVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            if(database != null){
                if(database.isEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
             view.visibility = View.INVISIBLE
            }
        }


    }


}