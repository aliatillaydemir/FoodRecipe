package com.ayd.recipeapp.util

import androidx.recyclerview.widget.DiffUtil
import com.ayd.recipeapp.model.Result

class RecipesDiffUtil<T>(
    private val oldList: List<T>,  //normalde List<Result> şeklinde kullanıyorduk ama <T> yaparak generic classa çevirdik çünkü farklı yerlerde de kullancaz
    private val newList: List<T>
): DiffUtil.Callback(){


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}