package com.hmduc.foody.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hmduc.foody.R
import com.hmduc.foody.data.database.enities.FavoritesEnity
import com.hmduc.foody.databinding.FavoriteRowBinding
import com.hmduc.foody.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorite_row.view.*
class FavoritesAdapter(private val context: FragmentActivity): RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEnity>()

    private var favorites = emptyList<FavoritesEnity>()

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
        val selectedRecipe = favorites[position]
        holder.bind(selectedRecipe)

        holder.itemView.favorite_row_card_view.setOnLongClickListener {
            context.startActionMode(this)
            true
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

    private fun applyStatusColor(color: Int) {
        context.window.statusBarColor = ContextCompat.getColor(context, color)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
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
        applyStatusColor(R.color.statusColor)
    }
}