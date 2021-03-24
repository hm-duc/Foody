package com.hmduc.foody.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.hmduc.foody.R
import com.hmduc.foody.adapter.PagerAdapter
import com.hmduc.foody.ui.fragment.ingredients.IngridientsFragment
import com.hmduc.foody.ui.fragment.introductions.IntroductionsFragment
import com.hmduc.foody.ui.fragment.overview.OverViewFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()

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
        }
        return super.onOptionsItemSelected(item)
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