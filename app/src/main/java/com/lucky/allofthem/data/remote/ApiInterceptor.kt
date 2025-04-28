package com.lucky.allofthem.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_VALUE = "application/json; charset=UTF-8"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .build()
        return chain.proceed(newRequest)
    }
}