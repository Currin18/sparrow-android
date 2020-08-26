package com.jesusmoreira.sparrow

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jesusmoreira.sparrow.controllers.TwitterController
import com.jesusmoreira.sparrow.utils.ImageUtil
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var twitterController: TwitterController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        twitterController = TwitterController(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        profileImage?.apply {
            // Set Color
            circleColor = Color.WHITE

            // Set Border
            borderWidth = 10f
            borderColor = Color.BLACK

            // Add Shadow
            shadowEnable = true
//            shadowRadius = 7f
//            shadowColor = Color.RED
//            shadowGravity = CircularImageView.ShadowGravity.CENTER
        }

        GlobalScope.launch(Dispatchers.Default) {
            twitterController.getUserProfile()?.let {user ->
                Log.d("TAG", "onCreate: ${user.name}")

                val bitmapProfile = user.profileImageURLHttps?.let {
                    ImageUtil.getBitmapFromURL(it)
                }
                val bitmapBackground = user.profileBannerURL?.let {
                    ImageUtil.getBitmapFromURL(it)
                }

                GlobalScope.launch(Dispatchers.Main) {
                    profileName.text = user.name
                    profileNick.text = "@${user.screenName}"
                    bitmapProfile?.let { profileImage.setImageBitmap(it) }
                    bitmapBackground?.let { profileHeader.background = BitmapDrawable(resources, it) }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}