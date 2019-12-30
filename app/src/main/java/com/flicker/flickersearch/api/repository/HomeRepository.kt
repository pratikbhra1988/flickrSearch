package com.flicker.flickersearch.api.repository

import com.flicker.flickersearch.BuildConfig
import com.flicker.domain.api.SearchResponse

/**
 * Created by Pratik Behera on 2019-12-30.
 */

class HomeRepository : BaseRepository() {

    suspend fun getSearchResult(searchText: String): com.flicker.domain.api.SearchResponse {
        return api.service.fetchResult("flickr.photos.search",BuildConfig.ApiKey,searchText,"json")
    }

}