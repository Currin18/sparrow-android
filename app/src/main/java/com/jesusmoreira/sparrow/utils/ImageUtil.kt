package com.jesusmoreira.sparrow.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL


object ImageUtil {
    suspend fun getBitmapFromURL(url: String?): Bitmap? =
        url?.let { getBitmapFromURL(URL(it)) }

    suspend fun getBitmapFromURL(url: URL?): Bitmap? =
        url?.let { BitmapFactory.decodeStream(it.openConnection().getInputStream()) }
}