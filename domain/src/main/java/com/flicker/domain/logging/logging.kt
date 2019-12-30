package com.flicker.domain.logging

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.flicker.domain.api.ParserProvider


fun logDebug(tag: Tag, message: () -> String) {
    DomainLogger.injected.debug(tag, message)
}

fun logWarning(tag: Tag, message: () -> String) {
    DomainLogger.injected.warning(tag, message)
}

fun logError(tag: Tag, message: () -> String) {
    DomainLogger.injected.error(tag, message)
}

fun Any.type() = this::class.java.simpleName!!

/**
 * Generates a diff visualizing a difference between two objects of the same type
 * NOTE: Objects are serialized to json (and flattened) for ease of comparison.
 * It's computationally expensive and should be used with moderation even for logging purposes.
 * For simplicity it's assumed both objects are always serialized to the same set of keys
 * (meaning nulls can't be skipped).
 */
fun <T> toDiff(object1: T, object2: T): String {
    if (object1 == object2) {
        return "[no difference]"
    }
    var map1 = emptyMap<String, String>()
    var map2 = emptyMap<String, String>()
    try {
        map1 = object1.asMap()
        map2 = object2.asMap()
    } catch (exception: IllegalArgumentException) {
        logWarning(Tag(Layer.UNSPECIFIED, Any()).method("toDiff")) {
            "Exception occurred during asMap execution\n $exception"
        }
    }
    val onlyBefore = map1
            .except(map2)
            .map { (key, value) -> "- $key\n\t$value" }
    val onlyAfter = map2
            .except(map1)
            .map { (key, value) -> "+ $key\n\t$value" }
    val modified = map1
            .keys
            .intersect(map2.keys)
            .filter { map1[it] != map2[it] }
            .map { "$it\n\t-\t${map1[it]}\n\t+\t${map2[it]}" }

    return (onlyBefore + modified + onlyAfter).joinToString("\n")
}

private fun Map<String, String>.except(subtracted: Map<String, String>) = (keys - subtracted.keys)
        .map { it to this[it]!! }
        .toMap()

private fun <T> T.asMap(prefix: String = ""): Map<String, String> = ParserProvider
        .instance
        .valueToTree<JsonNode>(this)
        .fields()
        .asSequence()
        .map {
            if (it.value is ObjectNode) {
                it.value.asMap("$prefix${if (prefix.isEmpty()) "" else "."}${it.key}.")
            } else {
                mapOf(("$prefix${it.key}") to it.value.toString())
            }
        }
        .toList()
        .flatMap { it.entries }
        .map { it.key to it.value }
        .toMap()
