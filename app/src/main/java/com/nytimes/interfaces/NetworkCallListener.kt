package com.nytimes.interfaces

import com.nytimes.model.NyTimeResponse

interface NetworkCallListener {

    interface View {

        fun onFetchDataStarted()

        fun onFetchDataCompleted()

        fun onFetchDataSuccess(response: NyTimeResponse?)

        fun onFetchDataError(e: Throwable?)
    }

    interface Presenter {

        fun loadData(alL_SECTIONS: String, days: String, apI_KEY: String)

        fun subscribe()

        fun unsubscribe()

        fun onDestroy()

    }
}
