package com.flicker.flickersearch.core.model

data class Data<T>(var status: DataStatus, var data: T? = null, var error: Throwable? = null)

enum class DataStatus {
    SUCCESS,
    ERROR,
    LOADING
}
