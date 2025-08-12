package edu.gvsu.cis357.spacex_rest

import edu.gvsu.cis357.spacex_rest.model.Company
import edu.gvsu.cis357.spacex_rest.model.LaunchItem
import retrofit2.Response
import retrofit2.http.GET

interface SpacexApi {
    // http://api.spacexdata.com/latest/company
    @GET("company")
    suspend fun getCompanyInfo(): Response<Company>

    @GET("launches")
    suspend fun getLaunches(): Response<List<LaunchItem>>
}
