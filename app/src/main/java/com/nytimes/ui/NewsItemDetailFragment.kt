package com.nytimes.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nytimes.R
import com.nytimes.model.NewsItem
import kotlinx.android.synthetic.main.activity_newsitem_detail.*
import kotlinx.android.synthetic.main.newsitem_detail.view.*

/**
 * A fragment representing a single NewsItem detail screen.
 * This fragment is either contained in a [NewsItemListActivity]
 * in two-pane mode (on tablets) or a [NewsItemDetailActivity]
 * on handsets.
 */
class NewsItemDetailFragment : Fragment() {

    private var item: NewsItem? = null
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

                item = arguments?.getParcelable(ARG_ITEM_ID)
                activity?.toolbar_layout?.title = item?.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         rootView = inflater.inflate(R.layout.newsitem_detail, container, false)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        rootView?.newsitem_detail?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView, request: WebResourceRequest): Boolean {
                return super.shouldOverrideUrlLoading(
                        view, request)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgressBar()

            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                hideProgressBar()
            }

        }
        rootView?.newsitem_detail?.loadUrl(item?.url)
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    private fun showProgressBar() {
        rootView?.loader?.setVisibility(View.VISIBLE)

    }

    private fun hideProgressBar() {
        rootView?.loader?.setVisibility(View.GONE)
    }

}
