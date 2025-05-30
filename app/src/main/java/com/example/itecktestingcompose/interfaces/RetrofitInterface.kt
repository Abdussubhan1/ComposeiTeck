package com.example.itecktestingcompose.interfaces


import com.example.itecktestingcompose.modelClasses.Battery
import com.example.itecktestingcompose.modelClasses.GetLocation
import com.example.itecktestingcompose.modelClasses.GetRelay
import com.example.itecktestingcompose.modelClasses.Ignition
import com.example.itecktestingcompose.modelClasses.NotificationHistory
import com.example.itecktestingcompose.modelClasses.Status
import com.example.itecktestingcompose.modelClasses.TrackerLocation
import com.example.itecktestingcompose.modelClasses.UpdateFCM_API
import com.example.itecktestingcompose.modelClasses.ValidateCnicResponse
import com.example.itecktestingcompose.modelClasses.ValidateDeviceResponse
import com.example.itecktestingcompose.modelClasses.VehicleDetails
import com.example.itecktestingcompose.modelClasses.cmdQueueCheck
import com.example.itecktestingcompose.modelClasses.postDataResponse
import com.example.itecktestingcompose.modelClasses.technicianLocation
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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
        @Part images: List<MultipartBody.Part>,
        @Part("V_ID") vehicleID: RequestBody,
        @Part("TECH_ID") technicianID: RequestBody,
        @Part("OBD") obd: RequestBody,
        @Part("IMMOBILIZER") immo: RequestBody,
        @Part("Customernumber") customerNumber: RequestBody,
        @Part("TLocID") trackerLocation: RequestBody,
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

    @FormUrlEncoded
    @POST("TrackerLocation.php")
    suspend fun getTrackerInstallLocation(): Response<TrackerLocation>
}


