package com.hmduc.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hmduc.foody.R
import com.hmduc.foody.adapter.PagerAdapter
import com.hmduc.foody.data.database.enities.FavoritesEntity
import com.hmduc.foody.ui.fragment.ingredients.IngridientsFragment
import com.hmduc.foody.ui.fragment.introductions.IntroductionsFragment
import com.hmduc.foody.ui.fragment.overview.OverViewFragment
import com.hmduc.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var savedFavorite: Boolean = false
    private var savedIdRecipe = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpPagerApdater()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !savedFavorite) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && savedFavorite) {
            removeFormFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0, args.result)
        mainViewModel.insertFavorites(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackbar("Saved.")
        savedFavorite = true
    }

    private fun showSnackbar(s: String) {
        Snackbar.make(detailLayout, s, Snackbar.LENGTH_SHORT).setAction("Okaey"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavorites.observe(this, { favoritesEnity ->
            try {
                for (savedRecipe in favoritesEnity) {
                    if (savedRecipe.result.recipeId == args.result.recipeId) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedIdRecipe = savedRecipe.result.recipeId
                        savedFavorite = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                        savedFavorite = false
                    }
                }
            } catch (e: Exception) {
                d("DetailActivity", e.message.toString())
            }
        })
    }

    private fun removeFormFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedIdRecipe, args.result)
        mainViewModel.deleteFavorites(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackbar("Removed form Favorites.")
        savedFavorite = false
    }

    private fun setUpPagerApdater() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngridientsFragment())
        fragments.add(IntroductionsFragment())

        val titles = ArrayList<String>()
        titles.add("OverView")
        titles.add("Ingredient")
        titles.add("Introduction")

        val bundle = Bundle()
        bundle.putParcelable("recipesBundle", args.result)

        val adapter = PagerAdapter(bundle,fragments,titles,supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}