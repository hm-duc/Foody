package com.hmduc.foody.bindingadapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.hmduc.foody.data.database.enities.FoodJokeEntity
import com.hmduc.foody.models.FoodJoke
import com.hmduc.foody.util.NetworkResult

class FoodJokeBinding {
    companion object {

        @BindingAdapter("apiResponse", "readDataBase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(view: View, apiResponse: NetworkResult<FoodJoke>, dataBase: List<FoodJokeEntity>) {
            when (apiResponse) {
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> view.visibility = View.INVISIBLE
                        is MaterialCardView -> view.visibility = View.VISIBLE
                    }
                }
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> view.visibility = View.VISIBLE
                        is MaterialCardView -> view.visibility = View.INVISIBLE
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (dataBase != null) {
                                if (dataBase.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                } else {
                                    view.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }

        @BindingAdapter("apiResponse4", "readDataBase4", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(view: View, apiResponse: NetworkResult<FoodJoke>?, dataBase: List<FoodJokeEntity>) {
            if (dataBase.isNotEmpty()) {
                view.visibility = View.VISIBLE
                if (view is TextView) {
                    if (apiResponse != null) {
                        view.text = apiResponse.message.toString()
                    }
                }
            }
            if (apiResponse is NetworkResult.Success) {
                view.visibility = View.INVISIBLE

            }
        }
    }
}