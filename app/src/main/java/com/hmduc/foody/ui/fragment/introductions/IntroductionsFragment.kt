package com.hmduc.foody.ui.fragment.introductions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.hmduc.foody.R
import com.hmduc.foody.models.Result
import com.hmduc.foody.util.Constants
import kotlinx.android.synthetic.main.fragment_introductions.view.*

class IntroductionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_introductions, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        view.introduction_webView.webViewClient = object: WebViewClient() {}
        val url: String = myBundle!!.sourceUrl
        view.introduction_webView.loadUrl(url)
        return view
    }
}