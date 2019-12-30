package com.flicker.flickersearch.logging

import android.os.Looper
import android.util.Log
import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.Logger
import com.flicker.domain.logging.Tag
import com.flicker.flickersearch.BuildConfig


/**
 * Created by Pratik Behera on 2019-09-03.
 */

class AndroidLogger : Logger {
    private enum class Level(val logFunction: (String, String) -> Unit) {
        DEBUG({ tag, message ->
            Log.d(tag, message)
        }),
        WARNING({ tag, message ->
            Log.w(tag, message)
        }),
        ERROR({ tag, message ->
            Log.e(tag, message)
        });

        fun isAtLeast(level: Level) = values().let {
            it.indexOf(this) >= it.indexOf(level)
        }
    }

    /**
     * In the release version only log calls of this severity (or above) will be executed
     * in order to minimize the impact on performance
     */
    private val minimumReleaseLevel = Level.WARNING

    private fun log(level: Level, tag: Tag, message: () -> String) {
        if (BuildConfig.DEBUG || level.isAtLeast(minimumReleaseLevel)) {
            level.logFunction(
                tag.toString(),
                message()
                    .checkIfSensitive(tag)
                    .checkIfOnMainThread(tag))
        }
    }

    override fun debug(tag: Tag, message: () -> String) = log(Level.DEBUG, tag, message)

    override fun warning(tag: Tag, message: () -> String) = log(Level.WARNING, tag, message)

    override fun error(tag: Tag, message: () -> String) = log(Level.ERROR, tag, message)

    private fun String.checkIfSensitive(tag: Tag) = when {
        contains("add_sensitive_data_name") -> {
            logError(tag) {
                "Detected an attempt of logging a value that contains full session token! This shouldn't happen, as it's sensitive data. Check the implementation."
            }
            "[censored]"
        }
        else -> this
    }

    private fun String.checkIfOnMainThread(tag: Tag): String {
        val isOnMainThread = Looper.myLooper() == Looper.getMainLooper()
        // it doesn't have to, but might indicate an error - worth looking into
        val isAlarming = isOnMainThread && setOf(Layer.INTERACTOR, Layer.NETWORK, Layer.DATABASE).contains(tag.layer)
        val prefix = when {
            isOnMainThread && isAlarming -> "[!⊤!]"
            isOnMainThread -> "[⊤]"
            else -> "[⊥]"
        }
        return "$prefix $this"
    }
}
