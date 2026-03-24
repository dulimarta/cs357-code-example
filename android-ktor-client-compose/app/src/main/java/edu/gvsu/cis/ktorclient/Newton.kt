package edu.gvsu.cis.ktorclient

import kotlinx.serialization.Serializable

/*
{
    "operation": "derive",
    "expression": "3x^3+3x",
    "result": "9 x^2 + 3"
}

 */

@Serializable
data class Newton(
    val operation: String,
    val expression: String,
    val result: String
)
