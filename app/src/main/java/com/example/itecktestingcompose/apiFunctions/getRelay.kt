package com.example.itecktestingcompose.apiFunctions

import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.interfaces.ServiceBuilder

data class relayResponse(
    var success: Boolean,
    var isLoading: Boolean,
    var message: String
)


suspend fun setRelayStatus(
    devID: String,
    cmd: String
): relayResponse {

    var isLoading:Boolean
    var message:String

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getRelay(devID, cmd)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            if (responseBody.Success) {
                message = responseBody.Message
                isLoading = false
                return relayResponse(true,isLoading, message)
            } else {
                message = responseBody.Message
                isLoading = false
                return relayResponse(false, isLoading, message)
            }

        } else {
            relayResponse(false,isLoading=false, message="Error Sending Command!")
        }


    } catch (e: Exception) {
        relayResponse(false,isLoading=false, message="Error Sending Command!")
    }

}
