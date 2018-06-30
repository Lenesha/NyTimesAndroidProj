package com.nytimes.manager

import com.nytimes.interfaces.NetworkCallListener
import com.nytimes.model.NyTimeResponse
import rx.Observer
import rx.Scheduler

class InteractionManager(
       private var callManager:NetworkCallManager,
        private var scheduler: Scheduler,
        private var observerthread: Scheduler,
        private var view: NetworkCallListener.View?) : NetworkCallListener.Presenter {

  override  fun loadData(alL_SECTIONS: String, days: String, apI_KEY: String) {
        view!!.onFetchDataStarted()

//        val call = NetworkCallManager()
//                .getNewsFeed(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, NytimesConstants.API_KEY)

//      call.enqueue(object : Callback<NyTimeResponse> {
//          override fun onResponse(call: Call<NyTimeResponse>,
//                                  response: Response<NyTimeResponse>) {
//              if (response.isSuccessful && response.body()!=null && response.body().status=="OK") {
//                  view!!.onFetchDataSuccess(response.body().results)
//                  view!!.onFetchDataCompleted()
//
//              }
//          }
//
//          override fun onFailure(call: Call<NyTimeResponse>?, t: Throwable?) {
//              view!!.onFetchDataError(t?.message)
//
//
//          }
//      })
      callManager
              .getNewsFeed(alL_SECTIONS,days,apI_KEY)
              .subscribeOn(scheduler)
              .observeOn(observerthread)
              .subscribe(object:Observer<NyTimeResponse>{
                  override fun onError(e: Throwable?) {
                      view!!.onFetchDataError(e)

                  }

                  override fun onNext(t: NyTimeResponse?) {
                                        view!!.onFetchDataSuccess(t)

                  }

                  override fun onCompleted() {
                      view!!.onFetchDataCompleted()

                  }
              });

  }

    override  fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override  fun onDestroy() {
        this.view = null
    }
}