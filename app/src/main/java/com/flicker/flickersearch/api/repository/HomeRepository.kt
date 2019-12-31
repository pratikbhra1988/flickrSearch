package com.flicker.flickersearch.api.repository

import com.flicker.domain.api.SearchDetailResponse
import com.flicker.flickersearch.BuildConfig
import com.flicker.domain.api.SearchResponse
import com.flicker.flickersearch.utils.API_KEY
import com.flicker.flickersearch.utils.FORMAT_JSON
import com.flicker.flickersearch.utils.METHOD_INFO
import com.flicker.flickersearch.utils.METHOD_SEARCH

/**
 * Created by Pratik Behera on 2019-12-30.
 */

class HomeRepository : BaseRepository() {

    suspend fun getSearchResult(searchText: String): SearchResponse {
        return api.service.fetchResult(METHOD_SEARCH,API_KEY,searchText,FORMAT_JSON)
    }


    suspend fun getDetailResult(photoID: String): SearchDetailResponse {
        return api.service.fetchDetailResult(METHOD_INFO,API_KEY,photoID,FORMAT_JSON)
    }

}