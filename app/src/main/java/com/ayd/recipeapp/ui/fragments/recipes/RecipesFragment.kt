package com.ayd.recipeapp.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayd.recipeapp.R
import com.ayd.recipeapp.viewModels.MainViewModel
import com.ayd.recipeapp.adapter.RecipesAdapter
import com.ayd.recipeapp.databinding.FragmentRecipesBinding
import com.ayd.recipeapp.util.Constants.Companion.API_KEY
import com.ayd.recipeapp.util.NetworkResult
import com.ayd.recipeapp.util.observeOnce
import com.ayd.recipeapp.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }


    //OnCreate'i kendimiz ekledik, OnCreateView'dan önce çağrılır bu. ViewModellarımızı init etmek için yaptık bunu.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

    }

    //fragment oluşturulduğunda otomatik olarak OnCreate değil OnCreateView oluşturulur.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecyclerView()
        readDatabase()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        return binding.root
    }

    private fun setUpRecyclerView(){

        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {

        lifecycleScope.launch{

            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment","readDatabase called!!")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            }

        }
    }

    private fun requestApiData(){
        Log.d("RecipesFragment","requestApiData called!!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }

    }

    private fun loadDataFromCache(){

        lifecycleScope.launch{

            mainViewModel.readRecipes.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mAdapter.setData(it[0].foodRecipe)
                }
            }
        }

    }

    private fun showShimmerEffect(){
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null  //avoid memory leaks
    }

}