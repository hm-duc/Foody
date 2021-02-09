package com.hmduc.foody.ui.fragment.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmduc.foody.viewmodels.MainViewModel
import com.hmduc.foody.R
import com.hmduc.foody.adapter.RecipesAdapter
import com.hmduc.foody.util.NetworkResult
import com.hmduc.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mview: View
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_recipes, container, false)

        setUpRecycleView()

        readDatbase()
        requestApiData()
        return mview
    }

    private fun readDatbase() {
        mainViewModel.readRecipes.observe(viewLifecycleOwner,{data ->
            if (data.isNotEmpty()) {
                mAdapter.setData(data[0].foodRecipe)
                hideShimmerEffect()
            } else {
                requestApiData()
            }
        })
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
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