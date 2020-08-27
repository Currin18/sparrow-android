package com.jesusmoreira.sparrow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusmoreira.sparrow.R
import kotlinx.android.synthetic.main.item_tweet.view.*
import twitter4j.ResponseList
import twitter4j.Status

class TweetAdapter(private val tweets: ResponseList<Status>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetText: TextView = itemView.tweetText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tweetText.text = tweets[position].text
    }

    override fun getItemCount(): Int = tweets.size
}