package com.itecknologi.itecktestingcompose.interfaces


import com.itecknologi.itecktestingcompose.modelClasses.Battery
import com.itecknologi.itecktestingcompose.modelClasses.EventLogCheck
import com.itecknologi.itecktestingcompose.modelClasses.GetLocation
import com.itecknologi.itecktestingcompose.modelClasses.GetRelay
import com.itecknologi.itecktestingcompose.modelClasses.Ignition
import com.itecknologi.itecktestingcompose.modelClasses.NotificationHistory
import com.itecknologi.itecktestingcompose.modelClasses.Status
import com.itecknologi.itecktestingcompose.modelClasses.TrackerLocation
import com.itecknologi.itecktestingcompose.modelClasses.UpdateFCM_API
import com.itecknologi.itecktestingcompose.modelClasses.ValidateCnicResponse
import com.itecknologi.itecktestingcompose.modelClasses.ValidateDeviceResponse
import com.itecknologi.itecktestingcompose.modelClasses.checkLogin
import com.itecknologi.itecktestingcompose.modelClasses.cmdQueueCheck
import com.itecknologi.itecktestingcompose.modelClasses.postDataResponse
import com.itecknologi.itecktestingcompose.modelClasses.technicianLocation
import com.itecknologi.itecktestingcompose.modelClasses.vehicleDetailsCheck
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
    @POST("get_batteryv1.php")
    suspend fun validateBattery(
        @Field("devid") devID: String,
        @Field("status") status: Int,
        @Field("event_log") eventLog: String
    ): Response<Battery>

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
        @Part("TLocId") trackerLocation: RequestBody
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
    @POST("technical_jobs_pending.php")
    suspend fun getVehicleDetails(
        @Field("T_ID") techID: String,
        @Field("type") type:String
    ): Response<vehicleDetailsCheck>

    @FormUrlEncoded
    @POST("update_FCM.php")
    suspend fun FCMUpdated(
        @Field("appid") appID: String,
        @Field("FcmToken") fcmtoken: String
    ): Response<UpdateFCM_API>
//isko change krdena bd m
    @FormUrlEncoded
    @POST("get_relayv1.php")
    suspend fun getRelay(
        @Field("devid") devID: String,
        @Field("cmd") cmd: String
    ): Response<GetRelay>
    //isko change krdena bd m
    @FormUrlEncoded
    @POST("cmd_queuev1.php")
    suspend fun getCmdQueueStatus(
        @Field("devid") devID: String,
        @Field("cmd") cmd: String
    ): Response<cmdQueueCheck>

    @POST("TrackerLocation.php")
    suspend fun getTrackerInstallLocation(): Response<TrackerLocation>

    @FormUrlEncoded
    @POST("check_login.php")
    suspend fun checkLogin(
        @Field("appid") appID: String,
        @Field("Appversion") appVersion: String
    ): Response<checkLogin>

    @POST("get_event_log.php")
    suspend fun eventLog(): Response<EventLogCheck>
}


