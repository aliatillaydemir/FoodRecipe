package com.ayd.recipeapp.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.ayd.recipeapp.databinding.FragmentInstructionsBinding
import com.ayd.recipeapp.model.Result
import com.ayd.recipeapp.util.Constants.Companion.RECIPE_RESULT_KEY


class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.instrucionWebView.webViewClient = object : WebViewClient(){}
        val webSiteUrl: String = myBundle!!.sourceUrl
        binding.instrucionWebView.loadUrl(webSiteUrl)


        return binding.root
    }



}