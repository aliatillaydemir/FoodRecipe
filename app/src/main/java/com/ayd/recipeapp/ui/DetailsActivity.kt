package com.ayd.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.ayd.recipeapp.R
import com.ayd.recipeapp.adapter.PagerAdapter
import com.ayd.recipeapp.databinding.ActivityDetailsBinding
import com.ayd.recipeapp.ui.fragments.ingredients.IngredientsFragment
import com.ayd.recipeapp.ui.fragments.instructions.InstructionsFragment
import com.ayd.recipeapp.ui.fragments.overview.OverviewFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()

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
        resultBundle.putParcelable("recipeBundle",args.result)

        val adapter = PagerAdapter(resultBundle,fragments,this)

        binding.viewPager2.adapter = adapter


        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab, position ->
            tab.text = titles[position]
        }.attach()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {  //back button works now
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }




}