package com.example.itecktestingcompose.APIFunctions

import android.graphics.Bitmap
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder
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


suspend fun submitData(
    cnic: String,
    name: String,
    mobileID: String,
    type: Int,
    appLoginID: String,
    images: ArrayList<Bitmap?>
): Boolean {
    var message = false

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
                imageParts
            )

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            message = responseBody.Success
        }

        message
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        message
    }
}






//suspend fun submitData(
//    cnic: String,
//    name: String,
//    mobileID: String,
//    type: Int,
//    appLoginID: Int,
//    images: ArrayList<Bitmap?>
//): Boolean {
//    var message = false
//
//    return try {
//        val base64Images = images.map { convertBitmapToBase64(it) }
//
//        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
//            .postData(cnic, name, mobileID, type, appLoginID, base64Images)
//
//        if (response.isSuccessful && response.body() != null) {
//            val responseBody = response.body()!!
//            message = responseBody.Success
//
//        }
//        message
//    } catch (e: Exception) {
//        Log.d("cnicV", "Exception: $e")
//        message
//    }
//
//}
//
//fun convertBitmapToBase64(bitmap: Bitmap?): String {
//    if (bitmap == null) return ""
//    val stream = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//    val byteArray = stream.toByteArray()
//    return Base64.encodeToString(byteArray, Base64.DEFAULT)
//}
