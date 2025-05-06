package com.example.itecktestingcompose.APIFunctions

import android.util.Log

import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder
import com.example.itecktestingcompose.ModelClasses.VehData

data class VehicleValidationResult(
    val ifDetailsExist: Boolean,
    var message: String,
    var data: List<VehData>
)


suspend fun getVehicleDetails(
    vehicleEngineChassis: String
): VehicleValidationResult {

    var data: List<VehData> = emptyList()
    var message: String = "Vehicle Not Found"
    var ifDetailsExist: Boolean = false

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getVehicleDetails(vehicleEngineChassis)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifDetailsExist=responseBody.Success
            message=responseBody.Message
            data = responseBody.Data ?: emptyList()
        }
        VehicleValidationResult(
            ifDetailsExist,
            message,
            data
        )

    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        VehicleValidationResult(
            ifDetailsExist,
            message,
            data
        )
    }

}
