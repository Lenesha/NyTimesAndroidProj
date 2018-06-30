package com.nytimes.manager


import com.nytimes.NytimesConstants
import com.nytimes.model.NyTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


interface RetrofitNetworkInterface {

  @GET(NytimesConstants.API_GETMOSTVIEWED)
  fun getNewsFeed(@Path("section") section : String,@Path("days") days : String, @Query("api-key") api_key:String): Observable<NyTimeResponse>
}