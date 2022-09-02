package com.ayd.recipeapp.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayd.recipeapp.adapter.FavoriteRecipesAdapter
import com.ayd.recipeapp.data.database.entities.FavoritesEntity
import org.jsoup.Jsoup

class FavoriteRecipesBinding {

    companion object{

        @BindingAdapter("viewVisibility","setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(view:View, favoritesEntity: List<FavoritesEntity>?, mAdapter: FavoriteRecipesAdapter?){

            if(favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            }else{
                when(view){
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }
            }

        }

        @BindingAdapter("parser")
        @JvmStatic
        fun parser(textView: TextView, description: String?){
            if(description != null){
                val desc = Jsoup.parse(description).text() // parse to html text -> </br> etc.
                textView.text = desc
            }
        }

    }


}