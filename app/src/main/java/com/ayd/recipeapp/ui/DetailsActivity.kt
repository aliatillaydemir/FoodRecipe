package com.ayd.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.ayd.recipeapp.R
import com.ayd.recipeapp.adapter.PagerAdapter
import com.ayd.recipeapp.data.database.entities.FavoritesEntity
import com.ayd.recipeapp.databinding.ActivityDetailsBinding
import com.ayd.recipeapp.ui.fragments.ingredients.IngredientsFragment
import com.ayd.recipeapp.ui.fragments.instructions.InstructionsFragment
import com.ayd.recipeapp.ui.fragments.overview.OverviewFragment
import com.ayd.recipeapp.util.Constants.Companion.RECIPE_RESULT_KEY
import com.ayd.recipeapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint //MainViewmodel'ın instance'ını oluşturuyoruz, dependency injection(inject)'ı olduğu için burada da notasyon olmalı.
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var  menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  //back button and detail text are visible now.

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY,args.result)

        val adapter = PagerAdapter(resultBundle,fragments,this)

        binding.viewPager2.adapter = adapter


        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {  //back button works now
        if(item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.save_to_favorites_menu && !recipeSaved){
            savedToFavorites(item)
        }else if(item.itemId == R.id.save_to_favorites_menu && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            try {
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.recipeId == args.result.recipeId){
                        changeMenuItemColor(menuItem,R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            }catch (e:Exception){
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }


    private fun savedToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0,args.result)
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item,R.color.yellow)
        showSnackBar("Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavorites(item:MenuItem){
        val favoritesEntity =
            FavoritesEntity(savedRecipeId,args.result)
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from favorites")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.detailsLayout,message,Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem,R.color.white)
    }


}