package com.nytimes.model

class NyTimeResponse (
        var status: String,
        var copyright: String,
        var num_results: String,
        var results: List<NewsItem>
)