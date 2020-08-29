package com.jesusmoreira.sparrow.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusmoreira.sparrow.MainActivity
import com.jesusmoreira.sparrow.R
import com.jesusmoreira.sparrow.adapters.TweetAdapter
import com.jesusmoreira.sparrow.utils.StringUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var homeViewModel: HomeViewModel
    private var tweetAdapter: TweetAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        GlobalScope.launch(Dispatchers.Default) {
            (context as? MainActivity)?.twitterController?.getTimeline()?.let {timeline ->
//                Log.d(TAG, "tweet: ${StringUtil.printObject(timeline.homeTimeline[0])}")
//                Log.d(TAG, "media: ${StringUtil.printObject(timeline.homeTimeline[0].mediaEntities)}")
//                Log.d(TAG, "symbol: ${StringUtil.printObject(timeline.homeTimeline[0].symbolEntities)}")
//                Log.d(TAG, "url: ${StringUtil.printObject(timeline.homeTimeline[0].urlEntities)}")
//                Log.d(TAG, "mention: ${StringUtil.printObject(timeline.homeTimeline[0].userMentionEntities)}")

                tweetAdapter = TweetAdapter(timeline.homeTimeline)
                withContext(Dispatchers.Main) {
//                GlobalScope.launch(Dispatchers.Main) {
                    timelineRecycler?.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = tweetAdapter
                    }
                }
            }
        }

        return root
    }
}