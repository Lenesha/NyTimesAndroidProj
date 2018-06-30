package com.nytimes.manager

import com.nytimes.NytimesConstants
import com.nytimes.model.NyTimeResponse
import retrofit2.Call
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable

class NetworkCallManager: RetrofitNetworkInterface {

    private val api: RetrofitNetworkInterface


    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(NytimesConstants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        this.api = retrofit.create<RetrofitNetworkInterface>(RetrofitNetworkInterface::class.java!!)
    }

    override fun getNewsFeed(section: String, days: String, api_key: String): Observable<NyTimeResponse> {
        return  this.api.getNewsFeed(section, days, api_key)
    }

}