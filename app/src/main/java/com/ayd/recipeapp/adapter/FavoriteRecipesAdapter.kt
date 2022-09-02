package com.ayd.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ayd.recipeapp.data.database.entities.FavoritesEntity
import com.ayd.recipeapp.databinding.FavoriteRecipesRowLayoutBinding
import com.ayd.recipeapp.util.RecipesDiffUtil

class FavoriteRecipesAdapter:RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    //list oluştur.
    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
               val layoutInflater = LayoutInflater.from(parent.context)
               val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //recyclerview layout kurulumu/bağlantısı
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //myviewholder'daki holder bağlanacak
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)
    }

    override fun getItemCount(): Int {
            //list size
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>){
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes,newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

}