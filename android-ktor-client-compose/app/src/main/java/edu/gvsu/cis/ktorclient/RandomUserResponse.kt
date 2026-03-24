package edu.gvsu.cis.ktorclient

import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(val info: Info)

@Serializable
data class Info(val seed: String,
    val results: Int,
    val page: Int,
    val version: String)
