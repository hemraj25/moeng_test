package com.hemraj.mo_engage.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.mo_engage.Injection
import com.hemraj.mo_engage.ViewModelFactory
import com.hemraj.mo_engage.R
import com.hemraj.mo_engage.Result
import com.hemraj.mo_engage.domain.NewsFeed
import kotlinx.android.synthetic.main.activity_main.*


private const val FILTER_CONST = 1001
private const val SORT_CONST = 1002
class NewsListActivity : AppCompatActivity(), GenericInfoBottomSheet.ICallback {

    private val TAG: String = NewsListActivity::class.java.canonicalName?:"NewsListActivity"


    private val authorList = arrayListOf<String>()

    private lateinit var genericBottomSheet: GenericInfoBottomSheet

    private val sortByTimeList = arrayListOf("OLD_TO_NEW", "NEW_TO_OLD")

    private var newsList: ArrayList<NewsFeed> = arrayListOf()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(Injection.provideDataRepo())
        ).get(NewsFeedViewModel::class.java)
    }

    private val newsFeedAdaptor = NewsFeedAdaptor {
        it?.feedUrl?.let { url ->
            if (!url.startsWith("https")) {
                launchWevView(url.replace("http", "https"))
            } else {
                launchWevView(url)
            }

        }
    }

    private fun launchWevView(url: String) {
        startActivity(WebViewActivity.createIntent(this, url))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initViews()
        initRecyclerView()
        initViewModel()
    }

    private fun initViews() {

        sortByTv.setOnClickListener {
            genericBottomSheet = GenericInfoBottomSheet.Builder()
                .setTitle(getString(R.string.sort_by))
                .setItemList(sortByTimeList)
                .setRequestCode(SORT_CONST)
                .setListenerCallBacks(this)
                .build()
            genericBottomSheet.show(this, TAG)
        }

        filterTv.setOnClickListener {
            if (authorList.isNotEmpty()) {
                genericBottomSheet = GenericInfoBottomSheet.Builder()
                    .setTitle(getString(R.string.filter))
                    .setItemList(authorList)
                    .setRequestCode(FILTER_CONST)
                    .setListenerCallBacks(this)
                    .build()
                genericBottomSheet.show(this, TAG)
            } else {
                Log.d(TAG, "emptyAuthorList")
            }
        }
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            title = getString(R.string.news_list)
        }
    }

    private fun initRecyclerView() {
        rvNewsList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvNewsList.adapter = newsFeedAdaptor
    }

    private fun initViewModel() {
        viewModel.getNewsList().observe(this, Observer {
            when (it.status) {

                Result.LOADING -> {
                }

                Result.SUCCESS -> {
                    newsList = it.data as ArrayList<NewsFeed>
                    authorList.clear()
                    newsList.forEach {
                        if (!authorList.contains(it.author)) {
                            authorList.add(it.author)
                        }
                    }
                    newsFeedAdaptor.setData(it.data)
                }

                Result.ERROR -> {
                }

            }
        })

        viewModel.fetchNewsList()
    }

    override fun onOptionSelected(reqCode: Int, text: String) {
        when (reqCode) {
            FILTER_CONST -> {
                newsFeedAdaptor.clearData()
                val filterAuthorList = newsList.filter { it.author.trim() == text.trim() }
                newsFeedAdaptor.setData(filterAuthorList)
                newsFeedAdaptor.notifyDataSetChanged()
            }

            SORT_CONST -> {
                newsFeedAdaptor.clearData()
                if (sortByTimeList[0] == text) {
                    val sortedAscList =
                        newsList.sortedWith(compareBy { getPublishedAtInMilliSec(it.publishedAt) })
                    newsFeedAdaptor.setData(sortedAscList)
                } else {
                    val sortedDescList =
                        newsList.sortedWith(compareBy { getPublishedAtInMilliSec(it.publishedAt) })
                            .asReversed()
                    newsFeedAdaptor.setData(sortedDescList)
                }
                newsFeedAdaptor.notifyDataSetChanged()
            }
        }

        genericBottomSheet.dismiss()
    }
}
