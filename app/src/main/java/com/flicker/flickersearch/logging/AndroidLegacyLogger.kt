package com.flicker.flickersearch.logging

import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.LegacyLogger
import com.flicker.domain.logging.Tag

/**
 * Created by Pratik Behera on 2019-09-03.
 */

class AndroidLegacyLogger : LegacyLogger.InjectableInstance {
    override fun debug(message: String?) {
        message?.let {
            logDebug(Tag(Layer.MIGRATION, this)) {
                it
            }
        }
    }

    override fun error(message: String?, exception: Throwable?) {
        val message = when {
            message != null && exception != null -> "$message:\n$exception"
            message != null || exception != null -> "${message.orEmpty()}${exception?.toString().orEmpty()}"
            else -> return
        }
        logError(Tag(Layer.MIGRATION, this)) {
            message
        }
    }
}
