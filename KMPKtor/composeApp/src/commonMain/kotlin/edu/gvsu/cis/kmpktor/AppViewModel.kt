package edu.gvsu.cis.kmpktor

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AppViewModel : ViewModel() {
    private val _randomUsers = MutableStateFlow<List<RandomUser>>(emptyList())
    val randomUsers = _randomUsers.asStateFlow()

    private val _randomQuotes = MutableStateFlow<List<RandomQuote>>(emptyList())
    val randomQuotes = _randomQuotes.asStateFlow()

    // The getHttpEngine() call in the argument below is optional.
    // The HTTPClient class automatically picks the platform specific engine
    val client = HttpClient(getHttpEngine()) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json() {
                ignoreUnknownKeys = true
            })
        }
    }

    fun getUser(count: Int = 4) {
        println("Getting  ${count} users")
        viewModelScope.launch(Dispatchers.IO) {
            val response = client.get("https://randomuser.me") {
                url {
                    appendPathSegments("api")
                    parameters.append("results", count.toString())
                    parameters.append("inc", "name,email,dob,picture")
                }
            }
            _randomUsers.update {
                val z = response.body<RandomUserResponse>()
                z.results
            }
        }
    }

    fun getQuotes(count: Int = 30) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = client.get("http://api.quotable.io") {
                url {
                    appendPathSegments("quotes", "random")
                    parameters.append("limit", count.toString())
                }
            }
            val z = response.body<List<RandomQuote>>()
            _randomQuotes.update { z }
//            z.forEach {
//                println(it.content)
//            }
        }
    }
}