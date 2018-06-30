package com.nytimes

import android.app.Application
import retrofit2.Retrofit

class NyTimesApplication: Application() {
    private var mRetrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()

    }



    fun getRetrofit(): Retrofit? {
        return mRetrofit
    }


}