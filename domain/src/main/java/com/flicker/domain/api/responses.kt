package com.flicker.domain.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

open class ApiResponse(
    @JsonProperty("ErrorCode") val errorCode: String? = null,
    @JsonProperty("ErrorData") val errorData: String? = null
) {
}
/**
 * Get Top up Details
 */


@JsonIgnoreProperties(ignoreUnknown = true)
class SearchResponse(
    @JsonProperty("errorCode") errorCode: String?,
    @JsonProperty("errorData") errorData: String?,
    @JsonProperty("success") val success: Boolean? = false,
    @JsonProperty("photos") val photos: photos? = null

) : ApiResponse(errorCode, errorData) {
}


@JsonIgnoreProperties(ignoreUnknown = true)
data class photos(
    @JsonProperty("page") val page: String? = null,
    @JsonProperty("pages") val pages: String? = null,
    @JsonProperty("perpage") val perpage: String? = null,
    @JsonProperty("total") val total: String? = null,
    @JsonProperty("photo") val listphotos: List<photo>?= null
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class photo(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("owner") val owner: String? = null,
    @JsonProperty("secret") val secret: String? = null,
    @JsonProperty("server") val server: String? = null,
    @JsonProperty("farm") val farm: String? = null,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("ispublic") val ispublic: String? = null,
    @JsonProperty("isfriend") val isfriend: String? = null,
    @JsonProperty("isfamily") val isfamily: String? = null,

    @JsonProperty("url_z") val url_n: String? = null,
    @JsonProperty("height_z") val height_n: String? = null,
    @JsonProperty("width_z") val width_n: String? = null
) {
}

//EndRegion










































