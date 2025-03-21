package com.example.itecktestingcompose.Interface

import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.ModelClasses.GetLocation
import com.example.itecktestingcompose.ModelClasses.ValidateCnicResponse
import com.example.itecktestingcompose.ModelClasses.ValidateDeviceResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface RetrofitInterface {
    @FormUrlEncoded
    @POST("validate_cnic.php")
    suspend fun validateCnic(@Field("cnic") cnic: String): Response<ValidateCnicResponse>
    @FormUrlEncoded
    @POST("validate_device.php")
    suspend fun validateDevice(@Field("devid") devID: String): Response<ValidateDeviceResponse>
    @FormUrlEncoded
    @POST("get_location.php")
    suspend fun validateLocation(@Field("devid") devID: String): Response<GetLocation>
}


object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseURL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}