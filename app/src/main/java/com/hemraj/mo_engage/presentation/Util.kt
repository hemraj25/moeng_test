package com.hemraj.mo_engage.presentation

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

private const val PUBLISHED_AT_FORMAT = "2020-02-10T13:42:00Z"


fun getPublishedAtInMilliSec(publishedAt: String): Long {
    return try {
        val formatter = SimpleDateFormat(PUBLISHED_AT_FORMAT, Locale.ENGLISH)
        formatter.parse(publishedAt)!!.time
    } catch (e: Exception) {
        Log.d("Util", e.stackTrace.toString())
        0L
    }
}