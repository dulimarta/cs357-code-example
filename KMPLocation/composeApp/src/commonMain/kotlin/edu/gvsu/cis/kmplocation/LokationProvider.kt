package edu.gvsu.cis.kmplocation

interface LokationProvider {
    suspend fun getCurrentLocation(): Pair<Double, Double>?
}

expect fun getLokationProvider(): LokationProvider