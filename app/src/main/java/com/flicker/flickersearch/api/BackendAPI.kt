package com.flicker.flickersearch.api

import com.flicker.flickersearch.BuildConfig
import com.flicker.domain.api.SearchResponse
import com.flicker.flickersearch.utils.MEDIUM_640
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Pratik Behera on 2019-12-30.
 */


class BackendAPI {

    interface Service {
        @GET("rest/")
        suspend fun fetchResult(
            @Query("method") method: String,
            @Query("api_key") api_key: String,
            @Query("text") text: String,
            @Query("format") format: String,
            @Query("extras") extras: String?= "url_$MEDIUM_640",
            @Query("nojsoncallback") nojsoncallback: String?="1"
        ): com.flicker.domain.api.SearchResponse


    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.EndPoint + "/")
        .addConverterFactory(JacksonConverterFactory.create(com.flicker.domain.api.ParserProvider.instance))
        .client(HttpClientProvider.create(60))

    val service: Service = retrofit.build().create(Service::class.java)
}