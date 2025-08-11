package edu.gvsu.cis357.spacex_graphql

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import edu.gvsu.cis357.spacex_graphql.adapter.AllLaunchesQuery_ResponseAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainViewModel: ViewModel() {
    val httpClient = OkHttpClient.Builder().build()
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://spacex-production.up.railway.app/")
        .okHttpClient(httpClient)
        .build()
    private val _ceoName = MutableStateFlow<String?>(null)
    val ceoName = _ceoName.asStateFlow()
    private val _launhces = MutableStateFlow<List<AllLaunchesQuery.Launch>>(emptyList())
    val launches = _launhces.asStateFlow()
    init {
        println("Init block is invoked")
    }

    fun sendCompanyQuery() {
        _ceoName.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val q = CEOQuery()
            val result = apolloClient.query(q).execute()
            println("GraphQL response ${result.data}")
            _ceoName.update {
                result.data?.company?.ceo
            }
        }
    }

    fun sendMissionQuery() {
        viewModelScope.launch(Dispatchers.IO) {
            val q = AllLaunchesQuery()
            val result = apolloClient.query(q).execute()
            _launhces.value = result.data?.launches?.mapNotNull { it } ?: emptyList()
        }
    }
}