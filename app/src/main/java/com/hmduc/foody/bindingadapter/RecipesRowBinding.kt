package com.hmduc.foody.bindingadapter

import android.service.controls.actions.ModeAction
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.google.android.material.card.MaterialCardView
import com.hmduc.foody.R
import com.hmduc.foody.models.Result
import com.hmduc.foody.ui.fragment.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup

class RecipesRowBinding{
    companion object {

        @BindingAdapter("onRecipesClickListener")
        @JvmStatic
        fun onRecipesClickListener(rowLayout: ConstraintLayout, result: Result) {
            rowLayout.setOnClickListener{
                val action = RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                rowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberofMinutes")
        @JvmStatic
        fun setNumberofMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, text: String?) {
            if (text != null) {
                val desc = Jsoup.parse(text).text()
                textView.text = desc
            }
        }
    }
}