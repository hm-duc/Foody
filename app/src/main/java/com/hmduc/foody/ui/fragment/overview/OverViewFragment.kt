package com.hmduc.foody.ui.fragment.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.hmduc.foody.R
import com.hmduc.foody.models.Result
import com.hmduc.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import kotlinx.android.synthetic.main.fragment_over_view.view.title_textView
import kotlinx.android.synthetic.main.recipes_row.view.*
import org.jsoup.Jsoup

class OverViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        myBundle?.summary.let {
            var summary = Jsoup.parse(it).text()
            view.summary_text.text = summary
        }
        view.overview_image.load(myBundle?.image)
        view.title_textView.text = myBundle?.title
        view.like_textview.text = myBundle?.aggregateLikes.toString()
        view.time_textview.text = myBundle?.readyInMinutes.toString()

        if (myBundle?.vegetarian == true) {
            view.vegatarian_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegatarian_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.vegan == true) {
            view.vegan_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegan_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            view.gluten_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.gluten_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            view.dairy_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dairy_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            view.healthy_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healthy_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true) {
            view.cheap_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheap_textview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        return view
    }
}