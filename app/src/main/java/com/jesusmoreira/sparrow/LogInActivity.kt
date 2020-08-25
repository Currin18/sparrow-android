package com.jesusmoreira.sparrow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jesusmoreira.sparrow.controllers.TwitterController
import com.jesusmoreira.sparrow.models.TwitterConstants
import com.jesusmoreira.sparrow.utils.PreferencesUtil
import kotlinx.coroutines.*
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class LogInActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    private lateinit var twitterController: TwitterController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        twitterController = TwitterController(this)

        GlobalScope.launch {
            val results = GlobalScope.async { twitterController.isLoggedIn() }
            val result = results.await()
            if (result) {
                // Show the Activity with the logged in user
                Log.d(TAG, "LoggedIn?: YES")
                startActivity(Intent(this@LogInActivity, MainActivity::class.java))
            } else {
                // Show the LogInActivity
                Log.d(TAG, "LoggedIn?: NO")
            }
        }
    }

    fun logInAccount(view: View) {
        twitterController.getRequestToken()
    }
}