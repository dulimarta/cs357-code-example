package edu.gvsu.cis357.spacex_rest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import edu.gvsu.cis357.spacex_rest.model.LaunchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.math.log

class MainViewModel : ViewModel() {
    private val _ceoName = MutableStateFlow<String?>(null)
    val ceoName = _ceoName.asStateFlow()

    private val _launches = MutableStateFlow<List<LaunchItem>>(emptyList())
    val launches = _launches.asStateFlow()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//    private val logger = HttpLoggingInterceptor()

    private val httpClient = OkHttpClient.Builder() /*.addInterceptor(logger)*/.build()

    val spacexApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/latest/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
            .create(SpacexApi::class.java)
    }

    init {
//        logger.level = HttpLoggingInterceptor.Level.BASIC
    }

    fun sendCompanyRequest() {
        _ceoName.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val result = spacexApiService.getCompanyInfo()
            println("Retrofit respose ${result.body()}")
            _ceoName.value = result.body()?.ceo
        }
    }

    fun sendLaunchesRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = spacexApiService.getLaunches().body()
            println("Retrofit response ${result?.size}")
            _launches.value = result ?: emptyList()
        }

    }
}