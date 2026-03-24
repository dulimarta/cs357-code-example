package edu.gvsu.cis.ktorclient

import android.net.wifi.WifiConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.Digest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MyViewModel: ViewModel() {
    private val _rawRespose = MutableStateFlow("")
    val rawResponse = _rawRespose.asStateFlow()

    val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    /* Two possible styles of including API keys in  */
    // curl --location 'https://api.thecatapi.com/v1/images/xxBaNrfM0/analysis' \
    //--header 'x-api-key: DEMO-API-KEY'

    // https://api.harvardartmuseums.org/person?apikey=3434343434

    fun getHarvardArt() {
        viewModelScope.launch(Dispatchers.IO) {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.harvardartmuseums.org"
                    path("person")
                    // Use the following statement when the API key must be included as a query parameter
                    parameters.append("apikey", "3434343434")
                }
                // Use this block when the API key must be included in the HTTP header
                headers {
                    append("x-api-key", "DEMO-API-KEY")
                }
            }
        }
    }
    // https://newton.vercel.app/api/v2/derive/3x%5E3+3x
    fun getNewtonExpression(e: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val z = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "newton.vercel.app"
                    path("api", "v2", "derive", e)
                }
            }
            val expr = z.body<Newton>()
            _rawRespose.update { expr.result }
        }
    }
    fun getSomething() {
        viewModelScope.launch(Dispatchers.IO) {
            //val z = client.get("https://randomuser.me/api?limits=1")
            val z = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "randomuser.me"
                    path("api")
                    parameters.append("limits", "1")
                    parameters.append("inc", "name,phone")
                }
            }
            withContext(Dispatchers.Main) {
                val resp = z.body<RandomUserResponse>()
                _rawRespose.update {
                    resp.info.seed
                }
            }

        }
    }

}