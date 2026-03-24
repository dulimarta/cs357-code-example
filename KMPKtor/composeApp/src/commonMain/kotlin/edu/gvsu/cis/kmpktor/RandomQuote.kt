package edu.gvsu.cis.kmpktor

import kotlinx.serialization.Serializable

@Serializable
data class RandomQuote(val _id: String, val content: String,
    val author: String, val tags: List<String>, val length: Int)
