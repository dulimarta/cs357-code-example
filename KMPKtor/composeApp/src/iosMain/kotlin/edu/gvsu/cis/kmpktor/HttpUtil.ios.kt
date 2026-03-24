package edu.gvsu.cis.kmpktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.LoggingFormat

actual fun getHttpEngine(): HttpClientEngine =
    Darwin.create {
    }

