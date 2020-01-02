package com.flicker.flickersearch.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.Tag
import com.flicker.flickersearch.R


/**
 * Created by Pratik Behera on 2019-12-31.
 */

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val tag = Tag(Layer.VIEW, this)

    init {
        inflate(context, R.layout.loading_screen, this)
    }

}