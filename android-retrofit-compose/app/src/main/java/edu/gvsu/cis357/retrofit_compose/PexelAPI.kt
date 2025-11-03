package edu.gvsu.cis357.retrofit_compose

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val avg_color: String,
    val liked: Boolean,
    val alt: String
)

data class PexelResponse(
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int,
    val next_page: String
)
data class PhotoSource (
    val original: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

interface PexelAPI {
//    @Headers("Authorization: uaWD4DFw0asfkNbAxMrMFjhn5PEH53uaaKBZuo6Qul6bQIXyzihmzicP")
    @Headers("Authorization: ${BuildConfig.PEXEL_API_KEY}")
    @GET("search")
    suspend fun photoSearch(@Query("query") searchKey: String): Response<PexelResponse>
}

object PexelClient {
    val clientBuilder = OkHttpClient.Builder()
    fun getInstance(): Retrofit {
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        clientBuilder.addInterceptor(logInterceptor)

        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }
}