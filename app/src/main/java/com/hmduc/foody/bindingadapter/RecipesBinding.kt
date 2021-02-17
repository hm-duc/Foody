package com.hmduc.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hmduc.foody.data.database.RecipesEnity
import com.hmduc.foody.models.FoodRecipe
import com.hmduc.foody.util.NetworkResult

class RecipesBinding {
    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(imageView: ImageView, apiReponse: NetworkResult<FoodRecipe>?, database: List<RecipesEnity>?) {
            if(apiReponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(textView: TextView, apiReponse: NetworkResult<FoodRecipe>?, database: List<RecipesEnity>?) {
            if(apiReponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiReponse.message.toString()
            } else {
                textView.visibility = View.INVISIBLE
            }
        }
    }
}