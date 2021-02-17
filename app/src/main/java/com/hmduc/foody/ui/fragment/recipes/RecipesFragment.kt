package com.hmduc.foody.ui.fragment.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmduc.foody.viewmodels.MainViewModel
import com.hmduc.foody.R
import com.hmduc.foody.adapter.RecipesAdapter
import com.hmduc.foody.databinding.FragmentRecipesBinding
import com.hmduc.foody.util.NetworkResult
import com.hmduc.foody.util.observerOnce
import com.hmduc.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { RecipesAdapter() }
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
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecycleView()
        readDatbase()
        requestApiData()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }
        return binding.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observerOnce(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    Log.d("RecipesFragment", "loadDataFromCache called")
                    mAdapter.setData(it[0].foodRecipe)
                }
            })
        }
    }

    private fun readDatbase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observerOnce(viewLifecycleOwner,{data ->
                if (data.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatbase called")
                    mAdapter.setData(data[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
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
                    loadDataFromCache()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecycleView() {
        binding.recyclerView.adapter = mAdapter
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

}