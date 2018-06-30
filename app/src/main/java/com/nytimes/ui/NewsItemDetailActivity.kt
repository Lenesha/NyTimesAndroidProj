package com.nytimes.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.nytimes.R
import kotlinx.android.synthetic.main.activity_newsitem_detail.*

/**
 * An activity representing a single NewsItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [NewsItemListActivity].
 */
class NewsItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsitem_detail)
        setSupportActionBar(detail_toolbar)


        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = NewsItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NewsItemDetailFragment.ARG_ITEM_ID,
                            intent.getParcelableExtra(NewsItemDetailFragment.ARG_ITEM_ID))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.newsitem_detail_container, fragment)
                    .commit()
        }
    }


}
