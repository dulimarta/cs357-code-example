package edu.gvsu.cis357.retrofit_compose

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Types for https://randomuser.me/api
data class Person(val name:Name, val email:String, val picture: Picture)
data class Name(val title:String, val first:String, val last:String)
data class Picture(val large:String, val medium:String, val thumbnail:String)
data class Info(val seed: String, @SerializedName("results") val count: Int, val page: Int, val Version:String)
data class RandomName(
    val results: List<Person>,
    val info: Info
    //or val info: Any
)

interface RandomUserApi {
    // Query string is     inc=name,email&results=N
    @GET("api/?inc=name,email,picture")
    suspend fun getRandomNames(@Query("results") N:Int): Response<RandomName>
}

val logInterceptor = HttpLoggingInterceptor()

object RandomUserClient {
    val BASE_URL = "https://randomuser.me/"
    val okHttpClientBuilder  = OkHttpClient.Builder()

    fun getInstance(): Retrofit {
        // These two lines are optional, but can be useful for debugging
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClientBuilder.addInterceptor(logInterceptor)

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }
}