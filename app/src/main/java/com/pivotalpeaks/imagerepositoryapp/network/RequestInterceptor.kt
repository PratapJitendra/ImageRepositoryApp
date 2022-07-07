package com.pivotalpeaks.imagerepositoryapp.network

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest=chain.request()
        val request=originalRequest.newBuilder().url(originalRequest.url).build()
        Log.d(TAG, "intercept: "+request.toString())
        return chain.proceed(request)
    }
}