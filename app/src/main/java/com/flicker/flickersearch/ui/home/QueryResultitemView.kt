package com.flicker.flickersearch.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.flicker.domain.RenderingView
import com.flicker.domain.api.photo
import com.flicker.flickersearch.R
import com.flicker.flickersearch.utils.*
import kotlinx.android.synthetic.main.activity_home_item.view.*

/**
 * Created by Pratik Behera on 2019-12-30.
 */


class QueryResultitemView(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs),
    RenderingView<photo> {

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_home_item, this)
    }

    @SuppressLint("StringFormatMatches")
    override fun render(viewState: photo) {
        viewState.let { mData ->

            if(!mData.url_n.isNullOrEmpty()) {
                item_image.loadImageFromLink(mData.url_n)
            } else {
                val thumbnailLink = getFlickrImageLink(mData.id, mData.secret, mData.server, mData.farm!!.toInt(), SMALL_360)
                item_image.loadImageFromLink(thumbnailLink)
            }

            if (!mData.width_n.isNullOrEmpty())
                item_image.setHeightRatio(calculateHeightRatio(mData.width_n!!, mData.height_n!!))

            if (!mData.title.isNullOrEmpty())
                item_text.text = mData.title
            else
                item_text.hide()

        }
    }
}