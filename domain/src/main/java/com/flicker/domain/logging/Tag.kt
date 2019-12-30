package com.flicker.domain.logging

/**
 * Created by Pratik Behera on 2019-09-03.
 */

/**
 * Logging tag composed of several hierarchical identifiers
 */
data class Tag(val layer: Layer, private val `object`: Any, private val method: String? = null) {

    override fun toString() = "$APP_TAG${if (layer == Layer.UNSPECIFIED) " " else " [$layer] "}[${`object`.javaClass.simpleName}]${method?.let { " $it" } ?: ""}"

    fun method(method: String) = copy(method = method)
}

const val APP_TAG = "[FlickerSearch]"
