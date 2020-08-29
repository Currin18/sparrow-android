package com.jesusmoreira.sparrow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusmoreira.sparrow.R
import com.jesusmoreira.sparrow.utils.ImageUtil
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.synthetic.main.item_tweet.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.ResponseList
import twitter4j.Status

class TweetAdapter(private val tweets: ResponseList<Status>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetProfileImage: CircularImageView = itemView.tweetProfileImage
        val tweetProfileName: TextView = itemView.tweetProfileName
        val tweetProfileNick: TextView = itemView.tweetProfileNick
        val tweetText: TextView = itemView.tweetText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(tweets[position]) {
            GlobalScope.launch(Dispatchers.Default) {
                val bitmapProfile = user.profileImageURLHttps.replace("_normal", "")?.let {
                    ImageUtil.getBitmapFromURL(it)
                }
                withContext(Dispatchers.Main) {
                    holder.tweetProfileImage.setImageBitmap(bitmapProfile)
                }
            }

            holder.tweetProfileName.text = user.name
            holder.tweetProfileNick.text = "@${user.screenName}"
            holder.tweetText.text = text
        }
    }

    override fun getItemCount(): Int = tweets.size
}