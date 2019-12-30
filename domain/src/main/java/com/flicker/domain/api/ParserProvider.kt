package com.flicker.domain.api

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * Created by Pratik Behera on 2019-09-03.
 */

/**
 * Provides an [ObjectMapper] (as a lazily instantiated singleton)
 * used for parsing Json responses from the API and configured specifically
 * for serializing responses into Kotlin data classes

 */
object ParserProvider {
    val instance: ObjectMapper by lazy {
        ObjectMapper()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)!!
    }
}

inline fun <reified T> parseJson(json: String) = ParserProvider.instance.readValue<T>(
    json,
    object : TypeReference<T>() { })
