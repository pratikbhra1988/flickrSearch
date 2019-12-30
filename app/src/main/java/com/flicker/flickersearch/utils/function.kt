package com.flicker.flickersearch.utils

import android.view.View
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

        /*Glide.with(context.applicationContext)  //2
            .load(link) //3
            .centerCrop() //4
            .placeholder(R.drawable.bottom_rounded_rect_transparent_black) //5
            .error(R.drawable.bottom_rounded_rect_transparent_black) //6
            .fallback(R.drawable.bottom_rounded_rect_transparent_black) //7
            .into(this) //8*/
    }
}

const val DEFAULT_PAGE_SIZE = 30
const val DEFAULT_QUERY = "nature"
const val API_KEY = "aa26679bb06bff46497df7f1e970b178"

const val METHOD_SEARCH_RECENT = "flickr.photos.getRecent"
const val METHOD_SEARCH = "flickr.photos.search"

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