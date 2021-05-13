package com.hmduc.foody.adapter

import android.util.Log.d
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hmduc.foody.R
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.databinding.FavoriteRowBinding
import com.hmduc.foody.ui.fragment.favorites.FavoriteFragmentDirections
import com.hmduc.foody.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorite_row.view.*

class FavoritesAdapter(val context: FragmentActivity): RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEnity>()

    private lateinit var actionMode: ActionMode

    private var favorites = emptyList<FavoritesEnity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    class MyViewHolder(private val binding: FavoriteRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesEnity: FavoritesEnity) {
            binding.favoriteEntity = favoritesEnity
            binding.executePendingBindings()
        }

        companion object {
            fun form(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRowBinding.inflate(layoutInflater, parent,false)
                return MyViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.form(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        val currentRecipe = favorites[position]
        holder.bind(currentRecipe)

        holder.itemView.favorite_row_constraint.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.favorite_row_constraint.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                context.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEnity>) {
        val favoritesDiff = RecipesDiffUtil(favorites, newFavoriteRecipes)
        val diffResult = DiffUtil.calculateDiff(favoritesDiff)
        favorites = newFavoriteRecipes
        diffResult.dispatchUpdatesTo(this)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEnity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            applyActionMode()
            changeRecipeStyle(holder, R.color.white, R.color.stroke_color)
        }
        else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackGroundLightColor, R.color.colorPrimary)
            applyActionMode()
        }
    }

    private fun applyActionMode() {
        if (selectedRecipes.isNullOrEmpty()) {
            actionMode.finish()
        } else {
            actionMode.title = "${selectedRecipes.size} item selected."
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.FavoriteRow.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
        holder.itemView.favorite_row_card_view.strokeColor =
            ContextCompat.getColor(context, strokeColor)
    }

    private fun applyStatusColor(color: Int) {
        context.window.statusBarColor = ContextCompat.getColor(context, color)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        actionMode = mode!!
        d("textsss", actionMode.toString())
        applyStatusColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
       return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach {
            changeRecipeStyle(it, R.color.white, R.color.stroke_color)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusColor(R.color.statusColor)
    }

    fun clearContextualActionMode() {
        if (this::actionMode.isInitialized) {
            actionMode.finish()
        }
    }
}