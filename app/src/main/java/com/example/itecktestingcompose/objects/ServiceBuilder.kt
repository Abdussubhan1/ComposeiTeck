package com.example.itecktestingcompose.objects

import com.example.itecktestingcompose.constants.Constants
import com.example.itecktestingcompose.interfaces.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}