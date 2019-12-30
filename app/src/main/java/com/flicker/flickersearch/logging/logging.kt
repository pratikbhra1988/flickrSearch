package com.flicker.flickersearch.logging

import android.app.Application
import android.os.Build
import android.os.Bundle
import com.flicker.domain.logging.Tag

val logger = AndroidLogger()

fun logDebug(tag: Tag, message: () -> String) {
    logger.debug(tag, message)
}

fun logWarning(tag: Tag, message: () -> String) {
    logger.warning(tag, message)
}

fun logError(tag: Tag, message: () -> String) {
    logger.error(tag, message)
}

fun Bundle?.dump(): String {
    return when {
        this == null -> "null"
        this.isEmpty -> "empty"
        else -> keySet()
            .sorted()
            .map { it to this[it] }.joinToString("\n") { (key, value) -> "'$key': '$value'" }
    }
}

// solely for logging purposes
fun trimMemoryLevelName(trimMemoryLevel: Int): String {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        return trimMemoryLevel.toString()
    }
    return when (trimMemoryLevel) {
        Application.TRIM_MEMORY_UI_HIDDEN -> "UI HIDDEN"
        Application.TRIM_MEMORY_COMPLETE -> "COMPLETE"
        Application.TRIM_MEMORY_MODERATE -> "MODERATE"
        Application.TRIM_MEMORY_BACKGROUND -> "BACKGROUND"
        else ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                when (trimMemoryLevel) {
                    Application.TRIM_MEMORY_RUNNING_LOW -> "RUNNING LOW"
                    Application.TRIM_MEMORY_RUNNING_MODERATE -> "RUNNING MODERATE"
                    Application.TRIM_MEMORY_RUNNING_CRITICAL -> "RUNNING CRITICAL"
                    else -> "UNRECOGNIZED ($trimMemoryLevel)"
                }
            } else {
                "UNRECOGNIZED ($trimMemoryLevel)"
            }
    }
}
