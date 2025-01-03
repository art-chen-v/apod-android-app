package com.artemchen.android.apod.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val TAG = "PhotoInterceptor"

object PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", "DEMO_KEY")
            .build()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        val response = chain.proceed(modifiedRequest)
        Log.i(TAG, "Outgoing request to ${newUrl.toUrl()}")
        Log.i(TAG, response.peekBody(2048).string())
        return response
    }
}