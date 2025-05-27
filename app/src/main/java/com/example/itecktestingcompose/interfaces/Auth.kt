package com.example.itecktestingcompose.interfaces

import com.example.itecktestingcompose.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Add authKey to the header if it exists
        if (Constants.authKey.isNotBlank()) {
            requestBuilder.addHeader(name = "Authorization", value = Constants.authKey)
        }

        return chain.proceed(requestBuilder.build())
    }
}