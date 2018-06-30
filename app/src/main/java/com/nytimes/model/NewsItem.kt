package com.nytimes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NewsItem(
        var id:String,
        var url: String,
        var title: String,
        var byline: String,
        var published_date: String
) : Parcelable