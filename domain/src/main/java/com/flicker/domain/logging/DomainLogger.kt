package com.flicker.domain.logging

/**
 * Created by Pratik Behera on 2019-09-03.
 */

class DomainLogger {
    companion object {
        /**
         * Android logger has to be injected from app module
         * since domain module is free from Android dependencies.
         *
         * Using [java.util.logging.Logger.getLogger] is another possibility
         * (output gets redirected to Android logger in run-time),
         * but current implementation provides higher consistency.
         */
        var injected: Logger = object : Logger {
            override fun debug(tag: Tag, message: () -> String) {
                // do nothing
            }

            override fun warning(tag: Tag, message: () -> String) {
                // do nothing
            }

            override fun error(tag: Tag, message: () -> String) {
                // do nothing
            }
        }
    }
}
