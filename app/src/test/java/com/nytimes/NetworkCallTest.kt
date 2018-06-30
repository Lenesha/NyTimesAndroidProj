package com.nytimes


import com.google.gson.Gson
import com.nytimes.manager.NetworkCallManager
import com.nytimes.model.NewsItem
import com.nytimes.model.NyTimeResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import rx.observers.TestSubscriber
import java.util.*

@RunWith(JUnit4::class)
class NetworkCallTest {

    lateinit var networkCallManager : NetworkCallManager
    lateinit var mockServer : MockWebServer
    lateinit var subscriber:TestSubscriber<NyTimeResponse>

    @Before @Throws fun setUp() {
        // Initialize mock webserver
        mockServer = MockWebServer()
        // Start the local server
        mockServer.start()


        // Initialized repository
        networkCallManager = NetworkCallManager()

         subscriber = TestSubscriber<NyTimeResponse>()
    }

    @Test fun testReturnSuccessResponse() {


        // Mock a response with status 200 and sample JSON output
        val mockReponse = MockResponse()
                .setResponseCode(200)
                .setBody( Gson().toJson(tempResults))
        // Enqueue request
        mockServer.enqueue(mockReponse)

        // Call the API
        networkCallManager.getNewsFeed(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, NytimesConstants.API_KEY).subscribe(subscriber)

        // No errors
        subscriber.assertNoErrors()
        // One list emitted
        subscriber.assertValueCount(1)



    }
    @Test fun testErrorApi() {


        val mockReponse = MockResponse()
                .setResponseCode(500)
                .setBody( Gson().toJson(tempResults))
        // Enqueue request
        mockServer.enqueue(mockReponse)

        // Call the API
        networkCallManager.getNewsFeed(NytimesConstants.ALL_SECTIONS, NytimesConstants.DAYS, "empty").subscribe(subscriber)

        // No errors
        subscriber.assertNoValues()


    }
    @After
    @Throws fun tearDown() {
        // We're done with tests, shut it down
        mockServer.shutdown()
    }


    private val tempResults: NyTimeResponse
        get() {
            val newsItem = Arrays.asList(NewsItem("hi","hello","title","byline","date"), NewsItem("hi","hello","title","byline","date"))

            val nyTimeResponse = NyTimeResponse("title","copyright","123",newsItem)

            return nyTimeResponse
        }
}