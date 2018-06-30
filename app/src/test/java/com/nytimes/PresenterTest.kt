package com.nytimes

import com.nytimes.interfaces.NetworkCallListener
import com.nytimes.manager.InteractionManager
import com.nytimes.manager.NetworkCallManager
import com.nytimes.manager.RetrofitNetworkInterface
import com.nytimes.model.NewsItem
import com.nytimes.model.NyTimeResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*

@RunWith(JUnit4::class)
class PresenterTest {


   lateinit var callManager : NetworkCallManager
   @Mock lateinit var viewCallback : NetworkCallListener.View
    lateinit var interactionManager : InteractionManager
    @Mock lateinit var networkInterface : RetrofitNetworkInterface


    @Before
    @Throws fun setUp(){
        callManager = NetworkCallManager()
        MockitoAnnotations.initMocks(this)
        interactionManager = InteractionManager( callManager, Schedulers.immediate(),Schedulers.immediate(),viewCallback)
    }

    @Test
    fun testBlogsReturnsList() {
        `when`(networkInterface.getNewsFeed(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, NytimesConstants.API_KEY))
                .thenReturn(Observable.just(tempResults))

        interactionManager.loadData(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, NytimesConstants.API_KEY)

        // Verify view method is called
        verify(viewCallback).onFetchDataSuccess(any(NyTimeResponse::class.java))
    }

    @Test fun testBlogsReturnsError() {
        `when`(networkInterface.getNewsFeed(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS,"empty"))
                .thenReturn(Observable.error(Exception()))

        interactionManager.loadData(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS,"empty")

        // View is never called
        verify(viewCallback).onFetchDataError(any())
    }

    private val tempResults: NyTimeResponse
        get() {
            val newsItem = Arrays.asList(NewsItem("hi","hello","title","byline","date"), NewsItem("hi","hello","title","byline","date"))

            val nyTimeResponse = NyTimeResponse("title","copyright","123",newsItem)

            return nyTimeResponse
        }

}