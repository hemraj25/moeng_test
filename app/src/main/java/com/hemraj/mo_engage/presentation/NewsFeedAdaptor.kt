package com.hemraj.mo_engage.presentation

import com.hemraj.mo_engage.domain.NewsFeed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.mo_engage.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_rv_item_view.view.*
import java.lang.Exception


class NewsFeedAdaptor(private val onItemClickListener: (NewsFeed?) -> Unit) :
    RecyclerView.Adapter<NewsFeedAdaptor.NewsViewHolder>() {

    private var newsFeedList: List<NewsFeed>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.news_rv_item_view, parent, false)
        )

    override fun getItemCount(): Int {
        Log.d("NewsFeedAdaptor", "getItemCount = ${newsFeedList?.size}")
        return newsFeedList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("NewsFeedAdaptor", "bind")
        holder.bindData(newsFeedList?.get(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(newsFeedList?.get(position))
        }
    }

    fun setData(newsList: List<NewsFeed>) {
        newsFeedList = newsList
        Log.d("NewsFeedAdaptor", "list size ${newsFeedList?.size}")
        notifyDataSetChanged()
    }

    fun clearData() {
        newsFeedList = null
        notifyDataSetChanged()
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(newsFeed: NewsFeed?) {
            Picasso.get()
                .load(newsFeed?.imageUrl)
                .placeholder(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_launcher_background
                    )!!
                )
                .into(itemView.newsFeedIv, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        Log.d("NewsFeedAdaptor",
                            "Picasso exception ${e?.stackTrace.toString()}")
                    }
                })


            itemView.titleTv.text = newsFeed?.title
            itemView.authorTv.text = newsFeed?.author
        }
    }
}