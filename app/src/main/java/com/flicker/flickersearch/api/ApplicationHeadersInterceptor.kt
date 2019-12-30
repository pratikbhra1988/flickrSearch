package com.flicker.flickersearch.api
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Pratik Behera on 2019-12-30.
 */

class ApplicationHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestHeaders = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(requestHeaders)
    }
}
