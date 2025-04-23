package com.example.itecktestingcompose.Interface

import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.ModelClasses.Battery
import com.example.itecktestingcompose.ModelClasses.GetLocation
import com.example.itecktestingcompose.ModelClasses.Ignition
import com.example.itecktestingcompose.ModelClasses.Status
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
    suspend fun validateCnic(
        @Field("cnic") cnic: String,
        @Field("DeviceId") mobileID: String,
        @Field("FcmToken") FCMToken: String,
        @Field("Appversion") appVersion: String,
        @Field("OSVersion") OSVersion: String,
        @Field("Brand") Brand: String
    ): Response<ValidateCnicResponse>

    @FormUrlEncoded
    @POST("validate_device.php")
    suspend fun validateDevice(@Field("devid") devID: String): Response<ValidateDeviceResponse>

    @FormUrlEncoded
    @POST("get_location.php")
    suspend fun validateLocation(@Field("devid") devID: String): Response<GetLocation>

    @FormUrlEncoded
    @POST("get_battery.php")
    suspend fun validateBattery(@Field("devid") devID: String): Response<Battery>

    @FormUrlEncoded
    @POST("get_ignition.php")
    suspend fun validateIgnition(@Field("devid") devID: String): Response<Ignition>


    @FormUrlEncoded
    @POST("get_status.php")
    suspend fun getStatus(@Field("devid") devID: String): Response<Status>

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