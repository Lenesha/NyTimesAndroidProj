package com.nytimes.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.nytimes.NytimesConstants
import com.nytimes.R
import com.nytimes.interfaces.NetworkCallListener
import com.nytimes.manager.InteractionManager
import com.nytimes.manager.NetworkCallManager

import com.nytimes.model.NewsItem
import com.nytimes.model.NyTimeResponse
import kotlinx.android.synthetic.main.activity_newsitem_list.*
import kotlinx.android.synthetic.main.newsitem_list_content.view.*
import kotlinx.android.synthetic.main.newsitem_list.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [NewsItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class NewsItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val mNewsList = ArrayList<NewsItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsitem_list)

        setSupportActionBar(toolbar)
        toolbar.title = title



        if (newsitem_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        newsitem_list.adapter = SimpleItemRecyclerViewAdapter(this, mNewsList, twoPane)


        if (!checkConnection(this)) {
            Toast.makeText(this, getString(R.string.no_internet_txt), Toast.LENGTH_SHORT).show()
        } else {

            initiateNetworkCall()


        }
    }


    private fun initiateNetworkCall(){
        InteractionManager( NetworkCallManager(),Schedulers.io(), AndroidSchedulers.mainThread(),object : NetworkCallListener.View{
            override fun onFetchDataStarted() {
                progress_bar.setVisibility(View.VISIBLE)
            }

            override fun onFetchDataCompleted() {
                progress_bar.setVisibility(View.INVISIBLE)

            }

            override fun onFetchDataSuccess(response: NyTimeResponse?) {

                updateAdapter(response!!.results)
            }

            override fun onFetchDataError(e: Throwable?) {
                Toast.makeText(getApplicationContext(),e?.message,Toast.LENGTH_SHORT).show()
                progress_bar.setVisibility(View.INVISIBLE)

            }

        }).loadData(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, NytimesConstants.API_KEY);
    }

    private fun updateAdapter(listOfFeeds: List<NewsItem>) {
        mNewsList.clear()
        mNewsList.addAll(listOfFeeds)
        newsitem_list.adapter?.notifyDataSetChanged()
    }

   

    class SimpleItemRecyclerViewAdapter(private val parentActivity: NewsItemListActivity,
                                        private val values: List<NewsItem>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as NewsItem
                if (twoPane) {
                    val fragment = NewsItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable(NewsItemDetailFragment.ARG_ITEM_ID, item)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.newsitem_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, NewsItemDetailActivity::class.java).apply {
                        putExtra(NewsItemDetailFragment.ARG_ITEM_ID, item)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.newsitem_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.title
            holder.contentView.text = item.byline
            holder.dateView.text = item.published_date

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.byline
            val dateView: TextView = view.date
        }
    }

     fun checkConnection(context: Context?): Boolean {
        var available = false
        try {
            if (context != null) {
                val conMgr = context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = conMgr.activeNetworkInfo
                if (activeNetwork != null && activeNetwork.state == NetworkInfo.State.CONNECTED) {
                    available = true

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return available
    }
}
