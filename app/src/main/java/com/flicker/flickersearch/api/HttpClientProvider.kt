package com.flicker.flickersearch.api

import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.Tag
import com.flicker.domain.logging.type
import com.flicker.flickersearch.BuildConfig
import com.flicker.flickersearch.logging.logDebug
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Pratik Behera on 2019-12-30.
 */

class HttpClientProvider {
    companion object {

        private val tag = Tag(Layer.NETWORK, this)

        private val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logDebug(tag) {
                    "Intercepted network activity: $message"
                }
            }
        })



        fun create(
            timeoutSeconds: Int,
            logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
        ): OkHttpClient {
            try {
                val here = tag.method("create")
                logDebug(here) { "About to create an instance of ${OkHttpClient::class.java.simpleName}" }
                return OkHttpClient
                    .Builder()
                    .readTimeout(timeoutSeconds.toLong(), TimeUnit.SECONDS)
                    .connectTimeout(timeoutSeconds.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(ApplicationHeadersInterceptor())
                    .apply {
                        if (BuildConfig.DEBUG) {
                            addInterceptor(logger.apply { level = logLevel })
                        }
                    }
                    .build()
                    .also {
                        logDebug(here) { "${it.type()} built successfully" }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                return OkHttpClient()
            }
        }
    }
}
