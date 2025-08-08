package com.itecknologi.itecktestingcompose.apiFunctions

import android.graphics.Bitmap
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull


fun createPartFromString(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaTypeOrNull())
}

//Convert each bitmap to JPEG
fun convertBitmapToMultipart(bitmap: Bitmap?, index: Int): MultipartBody.Part? {
    if (bitmap == null) return null

    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val byteArray = stream.toByteArray()
    val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("img[]", "image$index.jpg", requestBody)
}

data class submitDataResponse(
    var success: Boolean = false,
    var message: String? = "",
    var isLoading: Boolean = false
)


suspend fun submitData(
    cnic: String,
    name: String,
    mobileID: String,
    type: Int,
    appLoginID: String,
    images: ArrayList<Bitmap?>,
    vehicleID: String,
    technicianID: Int,
    obd: Int,
    immo: Int,
    customerNumber: String,
    trackerLocation: Int,
    technicalJobAssignedID: String
): submitDataResponse {
    return try {
        val imageParts = images.mapIndexedNotNull { index, bitmap ->
            convertBitmapToMultipart(bitmap, index)
        }

        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .postData(
                createPartFromString(cnic),
                createPartFromString(name),
                createPartFromString(mobileID),
                createPartFromString(type.toString()),
                createPartFromString(appLoginID),
                imageParts,
                createPartFromString(vehicleID),
                createPartFromString(technicianID.toString()),
                createPartFromString(obd.toString()),
                createPartFromString(immo.toString()),
                createPartFromString(customerNumber),
                createPartFromString(trackerLocation.toString()),
                createPartFromString(technicalJobAssignedID)
            )

        val body = response.body()

        if (body != null) {
            submitDataResponse(success = body.Success, message = body.Message, false)
        }else
            submitDataResponse(success = false, message = "Response is Null", false)


    } catch (e: Exception) {
        Log.e("submitData", "Exception: ${e.localizedMessage}", e)
        submitDataResponse(
            success = false,
            message = "Exception Error: ${e.localizedMessage}",
            false
        )
    }
}


