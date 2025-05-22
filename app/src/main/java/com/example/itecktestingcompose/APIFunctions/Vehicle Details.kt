package com.example.itecktestingcompose.APIFunctions

import android.util.Log

import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder
import com.example.itecktestingcompose.ModelClasses.VehData

data class VehicleValidationResult(
    val ifDetailsExist: Boolean,
    var message: String,
    var data: List<VehData>,
    var isLoading: Boolean
)


suspend fun getVehicleDetails(
    vehicleEngineChassis: String
): VehicleValidationResult {

    var data: List<VehData> = emptyList()
    var message = "Vehicle Not Found"
    var ifDetailsExist = false

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getVehicleDetails(vehicleEngineChassis)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifDetailsExist = responseBody.Success
            message = responseBody.Message
            if (ifDetailsExist) {
                data = responseBody.Data
            }
            return VehicleValidationResult(ifDetailsExist, message, data,false)
        } else
            VehicleValidationResult(
                ifDetailsExist,
                message,
                data,
                false
            )

    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        VehicleValidationResult(
            ifDetailsExist,
            message,
            data,
            false
        )
    }

}
