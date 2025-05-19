package com.example.itecktestingcompose.Interface

import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.ModelClasses.Battery
import com.example.itecktestingcompose.ModelClasses.GetLocation
import com.example.itecktestingcompose.ModelClasses.GetRelay
import com.example.itecktestingcompose.ModelClasses.Ignition
import com.example.itecktestingcompose.ModelClasses.NotificationHistory
import com.example.itecktestingcompose.ModelClasses.Status
import com.example.itecktestingcompose.ModelClasses.UpdateFCM_API
import com.example.itecktestingcompose.ModelClasses.ValidateCnicResponse
import com.example.itecktestingcompose.ModelClasses.ValidateDeviceResponse
import com.example.itecktestingcompose.ModelClasses.VehicleDetails
import com.example.itecktestingcompose.ModelClasses.cmdQueueCheck
import com.example.itecktestingcompose.ModelClasses.postDataResponse
import com.example.itecktestingcompose.ModelClasses.technicianLocation
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


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

    @Multipart
    @POST("get_log.php")
    suspend fun postData(
        @Part("CNIC") cnic: RequestBody,
        @Part("name") name: RequestBody,
        @Part("dev_id") mobileID: RequestBody,
        @Part("type") type: RequestBody,
        @Part("applogin") appLoginID: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<postDataResponse>

    @FormUrlEncoded
    @POST("get_notification_history.php")
    suspend fun getNotificationHistory(@Field("cnic") cnic: String): Response<NotificationHistory>

    @FormUrlEncoded
    @POST("Technical_location.php")
    suspend fun getTechnicianLocation(
        @Field("cnic") cnic: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("gpsstatus") gpsstatus: Int
    ): Response<technicianLocation>

    @FormUrlEncoded
    @POST("get_vehicle_details.php")
    suspend fun getVehicleDetails(@Field("search") vehicleEngineChassis: String): Response<VehicleDetails>

    @FormUrlEncoded
    @POST("update_FCM.php")
    suspend fun FCMUpdated(
        @Field("appid") appID: String,
        @Field("FcmToken") fcmtoken: String
    ): Response<UpdateFCM_API>

    @FormUrlEncoded
    @POST("get_relay.php")
    suspend fun getRelay(
        @Field("devid") devID: String,
        @Field("cmd") cmd: String
    ): Response<GetRelay>

    @FormUrlEncoded
    @POST("cmd_queue.php")
    suspend fun getCmdQueueStatus(
        @Field("devid") devID: String,
        @Field("cmd") cmd: String
    ): Response<cmdQueueCheck>


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