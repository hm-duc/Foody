package com.hmduc.foody.bindingadapter

import android.util.Log.d
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hmduc.foody.adapter.FavoritesAdapter
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.models.Result
import com.hmduc.foody.ui.fragment.favorites.FavoriteFragmentDirections

class FavoriteBinding {
    companion object {

        @BindingAdapter("onFavoritesClickListener")
        @JvmStatic
        fun onFavoritesClickListener(rowLayout: ConstraintLayout, result: Result) {
            rowLayout.setOnClickListener{
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(result)
                rowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisible(view: View, favoritesEntity: List<FavoritesEnity>?, mAdapter: FavoritesAdapter?) {
            if (favoritesEntity.isNullOrEmpty()) {
                d("testtt", favoritesEntity.toString())
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            } else {
                d("testtt", favoritesEntity.toString())
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }
}