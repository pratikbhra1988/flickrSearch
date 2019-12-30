package com.flicker.flickersearch.api.repository

import com.flicker.flickersearch.api.BackendAPI
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Pratik Behera on 2019-12-30.
 */

open class BaseRepository : KoinComponent {
    val api: BackendAPI by inject()
}