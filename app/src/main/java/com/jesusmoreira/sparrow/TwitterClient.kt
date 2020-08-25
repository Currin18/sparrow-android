package com.jesusmoreira.sparrow

import android.content.Context
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.oauth.OAuthBaseClient
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.api.BaseApi

class TwitterClient : OAuthBaseClient {
    companion object {
        val REST_API_INSTANCE: BaseApi<*> = TwitterApi.instance()
        const val REST_URL = "https://api.twitter.com/1.1"
        const val REST_CONSUMER_KEY = "9WSc2xRpzqyZi3mp6r8gkzMBE"
        const val REST_CONSUMER_SECRET = "iEXS45GPPNBpMamXtVyjlgUNjNKY6c31lWQurxAbvD6eKOeTCV"
        const val REST_CALLBACK_URL = "oauth://sparrow.jesusmoreira.com"
//        const val REST_CALLBACK_URL = "x-oauthflow-twitter://sparrow.jesusmoreira.com"
    }

    constructor(context: Context) : super(
        context, REST_API_INSTANCE, REST_URL,
        REST_CONSUMER_KEY, REST_CONSUMER_SECRET, null, REST_CALLBACK_URL
    )

    // ENDPOINTS BELOW

    fun getHomeTimeline(page: Int, handler: JsonHttpResponseHandler) {
        val apiUrl = getApiUrl("statuses/home_timeline.json")
        val params = RequestParams()
        params["page"] = page.toString()
        client.get(apiUrl, params, handler)
    }
}