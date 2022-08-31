package com.ayd.recipeapp.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.ayd.recipeapp.R
import com.ayd.recipeapp.databinding.FragmentOverviewBinding
import com.ayd.recipeapp.model.Result
import com.ayd.recipeapp.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.mainImageView.load(myBundle?.image)
        binding.titleTextView.text = myBundle?.title
        binding.likesTextView.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let { txt ->                     //html parser
            val summary = Jsoup.parse(txt).text()
            binding.summaryTextView.text = summary
        }


        if(myBundle?.vegetarian==true){
            binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }

        if(myBundle?.vegan==true){
            binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }

        if(myBundle?.glutenFree==true){
            binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }

        if(myBundle?.dairyFree==true){
            binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }

        if(myBundle?.veryHealthy==true){
            binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }

        if(myBundle?.cheap==true){
            binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.teal_200))
        }



        return binding.root
    }


}