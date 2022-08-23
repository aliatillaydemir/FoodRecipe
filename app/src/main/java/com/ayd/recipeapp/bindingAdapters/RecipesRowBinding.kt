package com.ayd.recipeapp.bindingAdapters

import android.graphics.Color.green
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.ayd.recipeapp.R

class RecipesRowBinding {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView:TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean){

            if(vegan){
                when(view){
                    is TextView ->{
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.teal_700))
                    }
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.teal_700))
                    }

                }  //switch case

            } //if vegan

        } //end of func.


    }


}