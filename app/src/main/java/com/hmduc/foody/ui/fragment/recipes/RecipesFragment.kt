package com.hmduc.foody.ui.fragment.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmduc.foody.MainViewModel
import com.hmduc.foody.R
import com.hmduc.foody.adapter.RecipesAdapter
import com.hmduc.foody.util.Constants
import kotlinx.android.synthetic.main.fragment_recipes.view.*

class RecipesFragment : Fragment() {

    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mview: View
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_recipes, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setUpRecycleView()

        return mview
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(applyQueries())
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apikey"] = Constants.API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun setUpRecycleView() {
        mview.recycler_view.adapter = mAdapter
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mview.recycler_view.showShimmer()
        mview.recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideShimmerEffect() {
        mview.recycler_view.hideShimmer()
    }

}