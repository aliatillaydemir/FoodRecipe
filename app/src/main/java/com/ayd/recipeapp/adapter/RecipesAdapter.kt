package com.ayd.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ayd.recipeapp.databinding.RecipesRowLayoutBinding
import com.ayd.recipeapp.model.FoodRecipe
import com.ayd.recipeapp.model.Result
import com.ayd.recipeapp.util.RecipesDiffUtil

class RecipesAdapter:RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result){
            binding.result = result               //recipes_row_layout'taki <variable> name
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)

        recipes = newData.results
        //notifyDataSetChanged() -> bunun yerine daha performanslı olan diffUtil classını yarattık, onu kullanacağız.

        diffUtilResult.dispatchUpdatesTo(this)
    }


}