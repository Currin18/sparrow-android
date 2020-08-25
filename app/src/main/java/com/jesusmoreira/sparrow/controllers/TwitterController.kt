package com.jesusmoreira.sparrow.controllers

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jesusmoreira.sparrow.models.TwitterConstants
import com.jesusmoreira.sparrow.models.User
import com.jesusmoreira.sparrow.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class TwitterController(val context: Context) {

    companion object {
        const val TAG = "TwitterController"
    }

    var twitter: Twitter? = null
    lateinit var twitterDialog: Dialog

    init {
        val accessToken = PreferencesUtil.getOauthToken(context)
        val accessTokenSecret = PreferencesUtil.getOauthTokenSecret(context)
        if (!accessToken.isNullOrEmpty() && !accessTokenSecret.isNullOrEmpty()) {
            val builder = ConfigurationBuilder()
                .setOAuthConsumerKey(TwitterConstants.CONSUMER_KEY)
                .setOAuthConsumerSecret(TwitterConstants.CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
            val config = builder.build()
            val factory = TwitterFactory(config)
            twitter = factory.instance
        }
    }

    fun getRequestToken() {
        GlobalScope.launch(Dispatchers.Default) {
            val builder = ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(TwitterConstants.CONSUMER_KEY)
                .setOAuthConsumerSecret(TwitterConstants.CONSUMER_SECRET)
                .setIncludeEmailEnabled(true)
            val config = builder.build()
            val factory = TwitterFactory(config)
            twitter = factory.instance
            try {
                twitter?.let { twitter ->
                    val requestToken = twitter.oAuthRequestToken
                    withContext(Dispatchers.Main) {
                        setupTwitterWebViewDialog(requestToken.authorizationURL)
                    }
                }
            } catch (e: IllegalStateException) {
                Log.e(TAG, "ERROR: getRequestToken: $e" )
            }
        }
    }

    suspend fun isLoggedIn(): Boolean {
        try {
            twitter?.let {
                withContext(Dispatchers.IO) { twitter?.verifyCredentials() }
                return true
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun getUserProfile() = withContext(Dispatchers.IO) {
        twitter?.verifyCredentials()
    }?.let { User(it) }

//        // Twitter Id
//        val twitterId = user.id.toString()
//        Log.d("Twitter Id: ", twitterId)
//
//        //Twitter Handle
//        val twitterHandle = user.screenName
//        Log.d("Twitter Handle: ", twitterHandle)
//
//        //Twitter Name
//        val twitterName = user.name
//        Log.d("Twitter Name: ", twitterName)
//
//        //Twitter Email
//        val twitterEmail = user.email
//        Log.d("Twitter Email: ",
//            twitterEmail
//                ?: "'Request email address from users' on the Twitter dashboard is disabled"
//        )
//
//        // Twitter Profile Pic URL
//        val twitterProfilePic = user.profileImageURLHttps.replace("_normal", "")
//        Log.d("Twitter Profile URL: ", twitterProfilePic)
//    }

    // Show twitter login page in a dialog
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupTwitterWebViewDialog(url: String) {
        twitterDialog = Dialog(context)
        val webView = WebView(context)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = TwitterWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        twitterDialog.setContentView(webView)
        twitterDialog.show()
    }

    // A client to know about WebView navigations
    // For API 21 and above
    @Suppress("OverridingDeprecatedMember")
    inner class TwitterWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith(TwitterConstants.CALLBACK_URL)) {
                Log.d(TAG, "Authorization URL: ${request?.url.toString()}")
                handleUrl(request?.url.toString())

                // Close the dialog after getting the oauth_verifier
                if (request?.url.toString().contains(TwitterConstants.CALLBACK_URL)) {
                    twitterDialog.dismiss()
                }
                return true
            }
            return false
        }

        // For API 19 and below
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(TwitterConstants.CALLBACK_URL)) {
                Log.d(TAG, "Authorization URL: $url")
                handleUrl(url)

                // Close the dialog after getting the oauth_verifier
                if (url.contains(TwitterConstants.CALLBACK_URL)) {
                    twitterDialog.dismiss()
                }
                return true
            }
            return false
        }

        // Get the oauth_verifier
        private fun handleUrl(url: String) {
            val uri = Uri.parse(url)
            val oauthVerifier = uri.getQueryParameter("oauth_verifier") ?: ""
            GlobalScope.launch(Dispatchers.Main) {
                val accToken = withContext(Dispatchers.IO) { twitter?.getOAuthAccessToken(oauthVerifier) }
                // Save the Access Token (accToken.token) and Access Token Secret (accToken.tokenSecret) using SharedPreferences
                // This will allow us to check user's logging state every time they open the app after cold start.
                PreferencesUtil.setOauthToken(context, accToken?.token ?: "")
                PreferencesUtil.setOauthTokenSecret(context, accToken?.tokenSecret ?: "")
            }
        }
    }

}