package com.flicker.flickersearch.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.flicker.flickersearch.R

/**
 * Created by Pratik Behera on 2019-12-30.
 */


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun getFlickrImageLink(id: String?, secret: String?, serverId: String?, farmId: Int?, size: String?): String {
    return "https://farm$farmId.staticflickr.com/$serverId/${id}_${secret}_$size.jpg"
}

 fun calculateHeightRatio(width_n: String, height_n: String): Float {
    val w = width_n.toInt()
    val h = height_n.toInt()

    return (h.toFloat() / w.toFloat())
}

fun ImageView.loadImageFromLink(link: String?) {
    if (!link.isNullOrEmpty()) {
        try {
            Glide.with(context.applicationContext)
                .load(link)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(this)
        } catch (ex:Exception) {

        }
    }
}

fun Activity.hideKeyboard() = currentFocus?.let {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(it.windowToken, 0)
}
fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var activeNetworkInfo: NetworkInfo? = null
    activeNetworkInfo = cm.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}



fun Context.isConnectingToInternet(): Boolean {
    val connectivity = getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity != null) {
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info)
                if (i.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
    }
    return false
}


const val DEFAULT_PAGE_SIZE = 30
const val DEFAULT_QUERY = "nature"
const val API_KEY = "d0d311847224fd6ba2baadc18fad624d"

const val FORMAT_JSON = "json"

const val METHOD_SEARCH_RECENT = "flickr.photos.getRecent"
const val METHOD_SEARCH = "flickr.photos.search"
const val METHOD_INFO = "flickr.photos.getInfo"

const val SMALL_SQUARE = "s"
const val LARGE_SQUARE = "q"
const val THUMBNAIL = "t"
const val SMALL_240 = "m"
const val SMALL_360 = "n"
const val MEDIUM_500 = "-"
const val MEDIUM_640 = "z"
const val MEDIUM_800 = "c"
const val MEDIUM_1024 = "b"
const val LARGE_1600 = "h"
const val LARGE_2048 = "k"