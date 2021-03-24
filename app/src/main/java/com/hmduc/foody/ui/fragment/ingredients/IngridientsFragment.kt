package com.hmduc.foody.ui.fragment.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmduc.foody.R
import com.hmduc.foody.adapter.IngredientsAdapter
import com.hmduc.foody.models.Result
import com.hmduc.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingridients.view.*

class IngridientsFragment : Fragment() {
    private val ingredientsAdapter: IngredientsAdapter  by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_ingridients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecycleView(view)

        myBundle?.extendedIngredients?.let {
            ingredientsAdapter.setData(it)
        }

        return view
    }

    private fun setupRecycleView(view: View) {
        view.ingredients_recycleview.adapter = ingredientsAdapter
        view.ingredients_recycleview.layoutManager = LinearLayoutManager(requireContext())
    }
}