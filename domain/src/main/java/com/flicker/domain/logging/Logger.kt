package com.flicker.domain.logging

/**
 * Created by Pratik Behera on 2019-09-03.
 */

interface Logger {
    fun debug(tag: Tag, message: () -> String)

    fun warning(tag: Tag, message: () -> String)

    fun error(tag: Tag, message: () -> String)
}
