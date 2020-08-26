package com.jesusmoreira.sparrow.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL


object ImageUtil {
    suspend fun getBitmapFromURL(url: String): Bitmap? =
        getBitmapFromURL(URL(url))

    suspend fun getBitmapFromURL(url: URL): Bitmap? =
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
}