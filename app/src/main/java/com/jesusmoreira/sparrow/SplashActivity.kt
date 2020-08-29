package com.jesusmoreira.sparrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jesusmoreira.sparrow.controllers.TwitterController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            val results = GlobalScope.async { TwitterController(this@SplashActivity).isLoggedIn() }
            val result = results.await()
            if (result) {
                // Show the Activity with the logged in user
                Log.d(LogInActivity.TAG, "LoggedIn?: YES")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                // Show the LogInActivity
                Log.d(LogInActivity.TAG, "LoggedIn?: NO")
                startActivity(Intent(this@SplashActivity, LogInActivity::class.java))
            }
        }
    }
}